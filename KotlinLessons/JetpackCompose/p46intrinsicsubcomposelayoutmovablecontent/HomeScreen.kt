package ru.korobeynikov.p46intrinsicsubcomposelayoutmovablecontent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(){
    //MovableContent
    Column {
        var vertical by remember { mutableStateOf(false) }
        Button(onClick = { vertical = !vertical }) {
            Text("Switch")
        }

        val content=remember {
            movableContentOf {
                ClickCounter(1)
                ClickCounter(2)
            }
        }

        Box{
            if(vertical){
                Column {
                    content()
                }
            } else {
                Row {
                    content()
                }
            }
        }
    }
}

@Composable
fun HomeScreenWithoutMovableContent(){
    Column {
        var vertical by remember { mutableStateOf(false) }
        Button(onClick = { vertical = !vertical }) {
            Text("Switch")
        }

        Box{
            if(vertical){
                Column {
                    ClickCounter(1)
                    ClickCounter(2)
                }
            } else {
                Row {
                    ClickCounter(1)
                    ClickCounter(2)
                }
            }
        }
    }
}

@Composable
fun ClickCounter(number: Int){
    var count by remember { mutableIntStateOf(0) }
    Button(onClick = { count++ }) {
        Text(text = "$number Clicks: $count")
    }
}

val profileShort=Profile(
    photo = "Some photo",
    name = "Some name",
    birthday = "Some birthday",
    location = "Some location"
)
val profileFull= Profile(
    photo = "Some photo",
    name = "Some name",
    birthday = "Some birthday",
    location = "Some location",
    email = "Some email",
    phone = "Some phone",
    website = "Some website",
    job = "Some job",
    company = "Some company"
)

@Composable
fun HomeScreenBoxWithConstraintsHeight300(){
    Box(modifier = Modifier
        .width(150.dp)
        .height(300.dp)
        .background(Color.LightGray)) {
        BoxWithConstraints {
            Column {
                if (this@BoxWithConstraints.maxHeight>=430.dp){
                    ProfileAvatar()
                }
                ProfileInfo(profileFull)
            }
        }
    }
}

@Composable
fun HomeScreenBoxWithConstraintsHeight450(){
    Box(modifier = Modifier
        .width(150.dp)
        .height(450.dp)
        .background(Color.LightGray)) {
        BoxWithConstraints {
            Column {
                if (this@BoxWithConstraints.maxHeight>=430.dp){
                    ProfileAvatar()
                }
                ProfileInfo(profileFull)
            }
        }
    }
}

@Composable
fun HomeScreenSubcomposeLayoutProfileShort(){
    Box(modifier = Modifier
        .width(150.dp)
        .height(270.dp)
        .background(Color.LightGray)) {
        SubcomposeLayout { constraints ->
            val placeableInfo=subcompose("info"){
                ProfileInfo(profileShort)
            }.first().measure(constraints)

            val infoHeight=placeableInfo.height
            var placeableAvatar: Placeable?=null
            if(infoHeight<380){
                placeableAvatar=subcompose("avatar"){
                    ProfileAvatar()
                }.first().measure(constraints)
            }
            val avatarHeight=placeableAvatar?.height ?: 0

            layout(constraints.maxWidth,infoHeight+avatarHeight){
                placeableAvatar?.placeRelative(0,0)
                placeableInfo.placeRelative(0,avatarHeight)
            }
        }
    }
}

@Composable
fun HomeScreenSubcomposeLayoutProfileFull(){
    Box(modifier = Modifier
        .width(150.dp)
        .height(270.dp)
        .background(Color.LightGray)) {
        SubcomposeLayout { constraints ->
            val placeableInfo=subcompose("info"){
                ProfileInfo(profileFull)
            }.first().measure(constraints)

            val infoHeight=placeableInfo.height
            var placeableAvatar: Placeable?=null
            if(infoHeight<380){
                placeableAvatar=subcompose("avatar"){
                    ProfileAvatar()
                }.first().measure(constraints)
            }
            val avatarHeight=placeableAvatar?.height ?: 0

            layout(constraints.maxWidth,infoHeight+avatarHeight){
                placeableAvatar?.placeRelative(0,0)
                placeableInfo.placeRelative(0,avatarHeight)
            }
        }
    }
}

@Composable
fun ProfileAvatar(){
    Icon(
        imageVector = Icons.Default.AccountCircle,
        contentDescription = null,
        modifier = Modifier.size(150.dp)
    )
}

@Composable
fun ProfileInfo(profile: Profile){
    Column {
        ProfileInfoItem("Name",profile.name)
        ProfileInfoItem("Birthday",profile.birthday)
        ProfileInfoItem("Location",profile.location)
        ProfileInfoItem("Email",profile.email)
        ProfileInfoItem("Phone",profile.phone)
        ProfileInfoItem("Website",profile.website)
        ProfileInfoItem("Job",profile.job)
        ProfileInfoItem("Company",profile.company)
    }
}

@Composable
fun ProfileInfoItem(label: String,text: String?){
    text?.let {
        Text(text = label, fontSize = 10.sp)
        Text(text = it, fontSize = 12.sp)
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun HomeScreenIntrinsicTextWithIcon(){
    Column(modifier = Modifier.width(IntrinsicSize.Max)) {
        Item("Item 1")
        Item("Item 22")
        Item("Item 333")
        Item("Item 4444")
        Item("Item 55555")
    }
}

@Composable
fun Item(text: String){
    Row {
        Text(text = text, fontSize = 20.sp)
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = null,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
fun HomeScreenIntrinsicSizeMax(){
    Text(
        text = "Some text for testing",
        fontSize = 20.sp,
        modifier = Modifier
            .width(IntrinsicSize.Max)
            .background(Color.LightGray)
    )
}

@Composable
fun HomeScreenIntrinsicSizeMin(){
    Text(
        text = "Some text for testing",
        fontSize = 20.sp,
        modifier = Modifier
            .width(IntrinsicSize.Min)
            .background(Color.LightGray)
    )
}