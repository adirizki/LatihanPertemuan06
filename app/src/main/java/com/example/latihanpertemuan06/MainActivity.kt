package com.example.latihanpertemuan06

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.latihanpertemuan06.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: MahasiswaAdapter
    var addRequestCode = 1
    var updateRequestCode = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvUser.setHasFixedSize(true)
        adapter = MahasiswaAdapter(this, ArrayList())
        binding.rvUser.adapter = adapter

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menu_add ->{
                val intent = Intent(this, InsertActivity::class.java)
                startActivityForResult(intent,addRequestCode)
                return true
            }else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == addRequestCode && resultCode == RESULT_OK) {
            val db = MahasiswaHelper(this)
            val mhsList = db.getData()
            adapter.updateData(mhsList)
        } else if(requestCode == updateRequestCode && resultCode == RESULT_OK) {
            val db = MahasiswaHelper(this)
            val mhsList = db.getData()
            adapter.updateData(mhsList)

        }
    }

    override fun onResume() {
        super.onResume()
        val db = MahasiswaHelper(this)
        val newMahasiswaList = db.getData()
        adapter.updateData(newMahasiswaList)
    }
}