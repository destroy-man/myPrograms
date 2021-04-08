package com.korobeynikov.authgoogle

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MainActivity : AppCompatActivity() {

    lateinit var mGoogleSignInClient: GoogleSignInClient

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    fun getGithubUsers(name:String,numPage:Int,resultListView:RecyclerView,buttonNext:Button,textNumPage:TextView){
        val repository = SearchRepositoryProvider.provideSearchRepository()
        repository.searchUsers(name,numPage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe ({
                    result ->

                        val resultList=mutableListOf<String>()
                        for(i in 0..result.items.size-1)
                            resultList.add(result.items[i].login+": "+result.items[i].html_url)
                        resultListView.adapter=CustomRecycleAdapter(resultList)

                    if(resultList.size>0) {
                        textNumPage.setText("Номер страницы: " + numPage+". Всего пользователей: "+result.total_count+".")
                        var countPages=0;
                        if(result.total_count%30==0)
                            countPages = result.total_count / 30
                        else
                            countPages = result.total_count / 30+1

                        if(numPage==countPages)
                            buttonNext.isEnabled=false
                        else
                            buttonNext.isEnabled=true
                    }
                    else {
                        textNumPage.setText("Поиск не дал результатов!")
                        buttonNext.isEnabled=false
                    }
                }, { error ->
                    error.printStackTrace()
                })
    }

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        mGoogleSignInClient= GoogleSignIn.getClient(this, gso)

        var firebaseAuth=FirebaseAuth.getInstance()
        var user: FirebaseUser? = firebaseAuth.getCurrentUser()

        val textName=findViewById<TextView>(R.id.textName)
        textName.setText(user?.displayName)

        val viewUser=findViewById<ImageView>(R.id.viewUser)
        Picasso.with(this).load(user?.photoUrl).into(viewUser)

        val resultListView:RecyclerView=findViewById(R.id.resultText)
        resultListView.layoutManager=LinearLayoutManager(this)

        var numPage=1

        var searchGithubUsers=findViewById<EditText>(R.id.searchGithubUsers)
        var textNumPage=findViewById<TextView>(R.id.textNumPage)
        val getUsers=findViewById<Button>(R.id.getUsers)
        val getPrevUsers=findViewById<Button>(R.id.getPrevUsers)
        val getNextUsers=findViewById<Button>(R.id.getNextUsers)

        searchGithubUsers.addTextChangedListener {
            if(searchGithubUsers.text.length==0){
                getUsers.isEnabled=false
                getPrevUsers.isEnabled=false
                getNextUsers.isEnabled=false
            }
            else
                getUsers.isEnabled=true
        }

        //Обработка кнопки Найти
        getUsers.setOnClickListener(View.OnClickListener {
            val timer = object: CountDownTimer(600, 1000) {
                override fun onTick(millisUntilFinished: Long) {}

                override fun onFinish() {
                    numPage = 1
                    getGithubUsers(searchGithubUsers.text.toString(), numPage, resultListView, getNextUsers, textNumPage)
                    getPrevUsers.isEnabled = false
                }
            }
            timer.start()
        })
        //Обработка кнопки Назад
        getPrevUsers.setOnClickListener(View.OnClickListener {
            val timer = object: CountDownTimer(600, 1000) {
                override fun onTick(millisUntilFinished: Long) {}

                override fun onFinish() {
                    numPage--
                    getGithubUsers(searchGithubUsers.text.toString(), numPage, resultListView, getNextUsers, textNumPage)
                    getNextUsers.isEnabled=true
                    if(numPage==1)
                        getPrevUsers.isEnabled=false
                }
            }
            timer.start()
        })
        //Обработка кнопки Вперед
        getNextUsers.setOnClickListener(View.OnClickListener {
            val timer = object: CountDownTimer(600, 1000) {
                override fun onTick(millisUntilFinished: Long) {}

                override fun onFinish() {
                    numPage++
                    getGithubUsers(searchGithubUsers.text.toString(), numPage, resultListView, getNextUsers, textNumPage)
                    if(!getPrevUsers.isEnabled)
                        getPrevUsers.isEnabled=true
                }
            }
            timer.start()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.exitProfile -> {
                mGoogleSignInClient.signOut().addOnCompleteListener {
                    val intent= Intent(this, LoginScreen::class.java)
                    startActivity(intent)
                    finish()
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}