package dev.bulean.notwallet.usecases

import dev.bulean.notwallet.data.AssetRepository
import dev.bulean.notwallet.domain.Error
import javax.inject.Inject

class GetAssetsUseCase @Inject constructor(private val repository: AssetRepository) {

    suspend operator fun invoke(): Error? {
        return repository.getAssets(
            "GOOGL,AAPL,BTC-USD,MELI,AMZN,TSLA,ETH-USD,UALA,PEYA,BNB-USD,USDC-USD," +
                    "ROKU,INTC,UBER,META,QCOM,USDT-USD,NVDA,DAI-USD,AMD,MCD,SBUX,MSFT,PYPL," +
                    "KO,MU,DIS,CRM,SPOT,AMC,PLTR,SNAP,ABNB,DOGE-USD,MRNA,AMAT,ADBE,NET,NFLX," +
                    "SHOP,SE,CSCO,COIN,MCHP,DBX,UMC,EBAY,GE,NKE,DDOG,ADDYY,NEWR,QS,UAA,U"
        )
    }
}
