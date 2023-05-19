package com.pickyberry.rtuitlab_recruit.presentation.coins_list


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.pickyberry.rtuitlab_recruit.R
import com.pickyberry.rtuitlab_recruit.domain.model.CoinItem
import com.pickyberry.rtuitlab_recruit.util.AccentRed
import com.pickyberry.rtuitlab_recruit.util.PositiveGreen
import kotlin.math.absoluteValue

@Composable
fun CoinItemComposable(
    coinItem: CoinItem,
    modifier: Modifier = Modifier,
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(coinItem.image)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.placeholder),
            contentDescription = stringResource(R.string.description),
            contentScale = ContentScale.Fit,
            modifier = Modifier.clip(CircleShape).size(width = 40.dp, height = 40.dp)
        )
        Spacer(modifier = Modifier.width(20.dp))
        Column(
            modifier = Modifier.weight(2f)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = coinItem.name,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = MaterialTheme.colors.onPrimary,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = coinItem.symbol,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onBackground
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "$" + coinItem.currentPrice,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            color = MaterialTheme.colors.onPrimary,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier.weight(2f)
        )
        Text(
            text = if (coinItem.priceChangePercentage > 0) "↑" + String.format(
                "%.1f",
                coinItem.priceChangePercentage
            ) + "%" else "↓" + String.format(
                "%.1f",
                coinItem.priceChangePercentage.absoluteValue
            ) + "%",
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            color = if (coinItem.priceChangePercentage > 0) PositiveGreen else AccentRed,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = if (coinItem.marketCapChangePercentage > 0) "↑" + String.format(
                "%.1f", coinItem.marketCapChangePercentage.absoluteValue
            ) + "%" else "↓" + String.format(
                "%.1f", coinItem.marketCapChangePercentage.absoluteValue
            ) + "%",
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            color = if (coinItem.marketCapChangePercentage > 0) PositiveGreen else AccentRed,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier.weight(1f)
        )
    }
}