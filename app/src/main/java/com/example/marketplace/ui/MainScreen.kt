package com.example.marketplace.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.marketplace.R

@Composable
fun MainScreen(
               onManualTestingClicked: () -> Unit,
               onFrontEndAutomationClicked: () -> Unit,
               onAPIAutomationClicked: () -> Unit,
               onAndroidAutomationClicked: () -> Unit,
               onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_login_logo),
            contentDescription = "Logo",
            modifier = Modifier
                .height(96.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(100.dp))

        Button(
            onClick = onManualTestingClicked,
            modifier = Modifier.width(200.dp)
        ) {
            Text("Ручное тестирование")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = onFrontEndAutomationClicked,
            modifier = Modifier.width(200.dp)
        ) {
            Text("Автоматизация FrontEnd")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = onAPIAutomationClicked,
            modifier = Modifier.width(200.dp)
        ) {
            Text("Автоматизация API")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = onAndroidAutomationClicked,
            modifier = Modifier.width(200.dp)
        ) {
            Text("Автоматизация Android")
        }

        Spacer(modifier = Modifier.height(50.dp))

        Button(
            onClick = onLogout,
            modifier = Modifier.width(200.dp)
        ) {
            Text("Выйти")
        }
    }
}
