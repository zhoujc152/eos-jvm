package com.memtrip.eos.chain.actions.transaction.transfer

import com.memtrip.eos.abi.writer.compression.CompressionType
import com.memtrip.eos.chain.actions.ChainResponse
import com.memtrip.eos.chain.actions.transaction.ChainTransaction
import com.memtrip.eos.chain.actions.transaction.TransactionContext
import com.memtrip.eos.chain.actions.transaction.abi.ActionAbi
import com.memtrip.eos.chain.actions.transaction.abi.TransactionAuthorizationAbi
import com.memtrip.eos.chain.actions.transaction.transfer.actions.TransferArgs
import com.memtrip.eos.chain.actions.transaction.transfer.actions.TransferBody
import com.memtrip.eos.http.rpc.ChainApi
import com.memtrip.eos.http.rpc.model.transaction.response.TransactionCommitted
import com.memtrip.eosio.abi.binary.gen.AbiBinaryGen
import io.reactivex.Single
import java.util.Arrays.asList

class TransferChain(chainApi: ChainApi) : ChainTransaction(chainApi) {

    data class Args(
        val fromAccount: String,
        val toAccount: String,
        val quantity: String,
        val memo: String
    )

    fun transfer(
        args: Args,
        transactionContext: TransactionContext
    ): Single<ChainResponse<TransactionCommitted>> {

        return push(
            transactionContext.expirationDate,
            asList(ActionAbi(
                "eosio.token",
                "transfer",
                asList(TransactionAuthorizationAbi(
                    transactionContext.authorizingAccountName,
                    "active")),
                transferBin(args)
            )),
            transactionContext.authorizingPrivateKey
        )
    }

    private fun transferBin(args: Args): String {
        return AbiBinaryGen(CompressionType.NONE).squishTransferBody(
            TransferBody(
                "eosio.token",
                "transfer",
                TransferArgs(
                    args.fromAccount,
                    args.toAccount,
                    args.quantity,
                    args.memo)
            )
        ).toHex()
    }
}