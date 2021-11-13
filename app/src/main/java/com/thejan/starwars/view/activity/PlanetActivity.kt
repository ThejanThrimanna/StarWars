package com.thejan.starwars.view.activity

import android.os.Bundle
import com.thejan.starwars.R
import com.thejan.starwars.databinding.ActivityPlanetBinding
import com.thejan.starwars.helper.PLANET
import com.thejan.starwars.helper.closeActivityLeftToRight
import com.thejan.starwars.model.Planet

class PlanetActivity : BaseActivity() {
    private lateinit var binding: ActivityPlanetBinding
    private lateinit var planet: Planet
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlanetBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initView()
        loadData()
    }

    private fun initView() {
        setToolBar(binding.toolbar, getString(R.string.planet_details), false)
    }

    private fun loadData() {
        planet = intent.getParcelableExtra(PLANET)!!
        binding.tvName.text = planet.name
        binding.tvOrbitalPeriod.text = planet.orbital_period
        binding.tvGravity.text = planet.gravity
    }

    override fun onBackPressed() {
        super.onBackPressed()
        closeActivityLeftToRight(this)
    }
}