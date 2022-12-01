package com.example.myapplicationsmartstudy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.myapplicationsmartstudy.Fragment.Dashboarsh
import com.example.myapplicationsmartstudy.Fragment.Lock_up
import com.example.myapplicationsmartstudy.Fragment.Profile
import com.example.myapplicationsmartstudy.Fragment.tern
import com.example.myapplicationsmartstudy.databinding.ActivityBottomBarBinding

class BottomBar : AppCompatActivity() {

    private lateinit var binding:ActivityBottomBarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBottomBarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(Dashboarsh())

        binding.bottomBar.setOnItemSelectedListener {

            when(it.itemId){
                R.id.home -> replaceFragment(Dashboarsh())
                R.id.lock_up -> replaceFragment(Lock_up())
                R.id.tern -> replaceFragment(tern())
                R.id.profile -> replaceFragment(Profile())
                else->{

                }
            }

            true
        }


    }

    private fun replaceFragment(fragment: Fragment)
    {
        val fragmentManager = supportFragmentManager
        val fragmentransaction = fragmentManager.beginTransaction()
        fragmentransaction.replace(R.id.framelayout,fragment)
        fragmentransaction.commit()
    }

}