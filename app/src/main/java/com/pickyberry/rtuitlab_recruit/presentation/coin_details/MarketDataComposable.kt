package com.pickyberry.rtuitlab_recruit.presentation.coin_details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pickyberry.rtuitlab_recruit.R
import com.pickyberry.rtuitlab_recruit.domain.model.MarketData

@Composable
fun MarketDataComposable(marketData: MarketData, currency: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            color = MaterialTheme.colors.background,
            shape = RoundedCornerShape(6.dp),
            border = BorderStroke(2.dp, MaterialTheme.colors.primary),
            modifier = Modifier.width(300.dp).padding(start = 10.dp, end = 10.dp),
        ) {
            Column {
                Spacer(modifier = Modifier.height(5.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(modifier = Modifier.width(150.dp)) {
                        Divider(color = MaterialTheme.colors.secondary, thickness = 4.dp)
                    }
                }

                if (marketData.marketCap?.usd != null && marketData.marketCap.rub != null)
                    MarketDataItem(
                        stringResource(R.string.market_cap),
                        if (currency == "USD") marketData.marketCap.usd else marketData.marketCap.rub,
                        currency
                    )

                if (marketData.fullyDilutedValuation?.usd != null && marketData.fullyDilutedValuation.rub != null)
                    MarketDataItem(
                        stringResource(R.string.fully_diluted_valuation),
                        if (currency == "USD") marketData.fullyDilutedValuation.usd else marketData.fullyDilutedValuation.rub,
                        currency
                    )

                if (marketData.totalVolume?.usd != null && marketData.totalVolume.rub != null)
                    MarketDataItem(
                        stringResource(R.string.total_volume),
                        if (currency == "USD") marketData.totalVolume.usd else marketData.totalVolume.rub,
                        currency
                    )
                if (marketData.totalSupply != null)
                    MarketDataItem(
                        stringResource(R.string.total_supply),
                        marketData.totalSupply
                    )
                if (marketData.maxSupply != null)
                    MarketDataItem(
                        stringResource(R.string.max_supply),
                        marketData.maxSupply
                    )
                if (marketData.circulatingSupply != null)
                    MarketDataItem(
                        stringResource(R.string.circulating_supply),
                        marketData.circulatingSupply
                    )
                Spacer(modifier = Modifier.height(5.dp))
            }
        }
    }
}

@Composable
fun MarketDataItem(name: String, value: Double, currency: String = "") {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = name,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = (if (currency == "USD") "$" else if (currency == "RUB") "₽" else "") + value.toLong().toString(),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        Column(modifier = Modifier.width(150.dp)) {
            Divider(color = MaterialTheme.colors.secondary, thickness = 4.dp)
        }
    }
}