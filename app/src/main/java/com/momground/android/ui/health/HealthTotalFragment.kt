package com.momground.android.ui.health

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.momground.android.common.BaseFragment
import com.momground.android.data.NutrientScore
import com.momground.android.databinding.FragmentHealthTotalBinding
import com.momground.android.data.enum_vo.NutrientDomain


class HealthTotalFragment: BaseFragment(), View.OnClickListener {
    val DOMAIN_SCORE_MAX = 1000
    var maxScore = 1000

    private var _binding: FragmentHealthTotalBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHealthTotalBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // score 점수 가져오기

        // 솔루션 결과 가져오기
        val sampleList = listOf(
            NutrientScore(NutrientDomain.PROTEIN,800f),
            NutrientScore(NutrientDomain.CARBOHYDRATE,930f),
            NutrientScore(NutrientDomain.FAT,840f),
            NutrientScore(NutrientDomain.DIETARY_FIBER,300f),
            NutrientScore(NutrientDomain.ETC,120f),
            NutrientScore(NutrientDomain.VITAMIN,200f)

        )
        configureNutrientChart(sampleList)

        return root
    }

    private fun configureNutrientChart(scoreList: List<NutrientScore>) {
        val ratios: ArrayList<Float> = arrayListOf()
        for(item in scoreList){
            val correction = normalize(item.score) * 0.9f + 0.1f
            ratios.add(correction)
        }

        val labels: ArrayList<String> = arrayListOf()
        for (item in scoreList){
            labels.add(item.nutrientDomain!!.areaText)
        }

        binding.chartRadar.setRatios(ratios)
        binding.chartRadar.setLabels(labels)
    }

    private fun regularize(input: Double): Double {
        return input * DOMAIN_SCORE_MAX / maxScore
    }
    private fun normalize(value: Float): Float {
        return value / DOMAIN_SCORE_MAX
    }

    override fun onClick(view: View?) {

    }

    internal fun newInstant() : HealthTotalFragment
    {
        val args = Bundle()
        val frag = HealthTotalFragment()
        frag.arguments = args
        return frag
    }
}
