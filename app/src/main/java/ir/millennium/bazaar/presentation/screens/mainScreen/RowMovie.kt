package ir.millennium.bazaar.presentation.screens.mainScreen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ir.millennium.bazaar.R
import ir.millennium.bazaar.data.model.remote.MovieItem
import ir.millennium.bazaar.presentation.ui.theme.LocalCustomColorsPalette
import ir.millennium.bazaar.presentation.utils.Constants.BASIC_URL_IMAGE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun rowMovie(
    movieItem: MovieItem,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope
) {

    val context = LocalContext.current

    Card(
        modifier = Modifier
            .padding(top = 6.dp)
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.size_radius_card_view)))
            .clickable {
                coroutineScope.launch { movieItem.title?.let { snackbarHostState.showSnackbar(it) } }
            },
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.size_radius_card_view)),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            ConstraintLayout(modifier = Modifier.fillMaxWidth()) {

                val (imageRef, titleRef) = createRefs()

                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data("$BASIC_URL_IMAGE${movieItem.posterPath}")
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .constrainAs(imageRef) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.value(120.dp)
                            height = Dimension.value(155.dp)
                        }
                        .fillMaxSize()
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.size_radius_card_view)))
                        .border(
                            0.dp,
                            Color.Transparent,
                            shape = RoundedCornerShape(dimensionResource(id = R.dimen.size_radius_card_view))
                        ),
                    contentScale = ContentScale.Crop
                )

                movieItem.title?.let {
                    Text(
                        text = it,
                        modifier = Modifier
                            .constrainAs(titleRef) {
                                top.linkTo(imageRef.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                            .padding(top = 4.dp, start = 8.dp, end = 8.dp),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Normal,
                        color = LocalCustomColorsPalette.current.textColorPrimary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}