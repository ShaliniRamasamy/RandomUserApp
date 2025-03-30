package com.example.randomusersapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.randomusersapp.R
import com.example.randomusersapp.domain.model.Result
import com.example.randomusersapp.view.component.InfoCard
import com.example.randomusersapp.view.component.UserInfoCard

@Composable
fun UserDetail(user: Result) {
   DetailsView(user = user)
}

@Composable
fun DetailsView(user: Result) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.white))
    ) {

        item {
            user.apply {
                AsyncImage(
                    model = user.picture.large,
                    contentDescription = "Item Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(346.dp),
                    contentScale = ContentScale.FillWidth
                )
                Spacer(modifier = Modifier.height(8.dp))
                UserInfoCard(user)
            }
        }

        item {
            user.apply {
                Title(title = "More info")
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp, 16.dp, 0.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    InfoCard(title = "Age", value = user.dob.age.toString().plus(" yrs"), modifier = Modifier.weight(1f))
                    InfoCard(title = "Nationality", value = user.nat, modifier = Modifier.weight(1f))
                    InfoCard(title = "Gender", value = user.gender, modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun Title(title: String) {
    Text(
        text = title,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp, 0.dp, 0.dp),
        color = colorResource(id = R.color.black),
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.W600,
        textAlign = TextAlign.Start
    )
}