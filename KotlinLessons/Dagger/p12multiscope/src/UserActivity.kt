package ru.korobeynikov.p12multiscope

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import ru.korobeynikov.p12multiscope.di.user.UserComponent
import javax.inject.Inject

class UserActivity : ComponentActivity() {

    @Inject
    lateinit var userFactory: UserComponent.UserFactory

    lateinit var userComponent: UserComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //MultiScope для get-методов
        //userComponent = (application as App).appComponent.getUserFactory().create(this)

        (application as App).appComponent.injectUserActivity(this)
        userComponent=userFactory.create(this)
        val uiHelperUser=userComponent.getUiHelper()

        setContent {
            Column(modifier = Modifier.safeContentPadding()) {
                Text("This is User Activity")
                Text("ui helper user: ${uiHelperUser.hashCode()}")
            }
        }
    }
}