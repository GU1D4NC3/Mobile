package com.momground.android.ui.home

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.momground.android.R
import com.momground.android.data.NewsItem
import com.momground.android.databinding.FragmentHome2Binding
import com.momground.android.ui.news.NewsAdapter


class HomeFragment : Fragment() {

    private var _binding: FragmentHome2Binding? = null
    private val binding get() = _binding!!
    var countDownTimer: CountDownTimer? = null

    //NEWS
    private lateinit var adapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHome2Binding.inflate(inflater, container, false)
        val root: View = binding.root

        setTheme()
        initNutrientPieChart()


        return root
    }
    private fun setTheme(){
        adapter = NewsAdapter(requireActivity().supportFragmentManager, requireContext(), mockData())
        initNewsLetter(adapter, binding.listNews)
    }

    private fun initNewsLetter(adapter: NewsAdapter, recyclerView: RecyclerView){
        recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
    }


    private fun mockData(): List<NewsItem> {
        return mutableListOf(
            NewsItem("임산부 추천 식단 총정리!", "초기","내용","","https://t1.daumcdn.net/cfile/tistory/236AC940578474921E",
                "질문입니다", "selection","1111,11111,2222,333","11111",false),

            NewsItem("음식 선택이 태아에 미치는 영향", "식단","*태아성장 및 모체 변화\n임신 첫 12주 또는 첫 3개월 동안, 일반적으로 산모는 1~2kg이 증가하나 혹시 입덧이 있으면 체중은 이보다 덜 증가할 것입니다.\n" +
                    "이 체중의 대부분은 태반과 가슴, 자궁, 그리고 여분의 혈액 생성 등으로 인해 나타납니다.","","https://www.obonparis.com/uploads/le%20spicy%20home/SPICY%20HOME23.jpg",
                "질문입니다","selection","1,2,3,4","1",false),

            NewsItem("엽산을 충분히 섭취하고 있나요?", "식단","내용","","https://www.ibabynews.com/news/photo/202106/95723_45378_027.jpg",
                "질문입니다", "selection","1111,11111,2222,333","11111",false)
        )
    }

    private fun initNutrientPieChart() {
        // 데이터 추가는 Entry 클래스를 이용한다.
        // PieChart를 이용할 것이기 때문에 PieEntry를 활용해 입력할 데이터를 설정한다.
        val nutrientRatio = listOf(
            PieEntry(10f),
            PieEntry(90f),
        )

        // slice의 색상을 설정해주기 위해 색상값 list를 생성한다.
        val pieColors = listOf(
            resources.getColor(R.color.orange, null),
            resources.getColor(R.color.back_white1, null)
        )

        // nutrientRatio 데이터의 개별적인 스타일링을 위해 DataSet을 생성한다.
        // ""에는 label 자리인데 필요가 없어서 빈 문자열로 뒀다.
        val dataSet = PieDataSet(nutrientRatio, "")

        // slice의 색상을 설정해준다.
        dataSet.colors = pieColors

        // true로 설정하면 slice 위에 Entry로 설정한 값이 보여진다.
        // 만들어야하는 디자인에는 값이 필요없어 false로 설정해줬다.
        dataSet.setDrawValues(false)

        // description.isEnabled : 차트 설명 유무 설정
        // legend.isEnabled : 범례 유무 설정
        // isRotationEnabled : 차트 회전 활성화 여부 설정
        // holeRadius : 차트 중간 구멍 크기 설정
        // setTouchEnabled : slice 터치 활성화 여부 설정
        // animateY(1200, Easing.EaseInOutCubic) : 애니메이션 시간, 효과 설정
        binding.progress.apply {
            data = PieData(dataSet)
            description.isEnabled = false
            legend.isEnabled = false
            isRotationEnabled = true
            isDrawHoleEnabled = true
            holeRadius = 90f
            setTouchEnabled(false)
            setHoleColor(R.color.white)
            animateY(1200, Easing.EasingOption.EaseInOutCubic)

            // animate()를 호출하면 차트를 새로 고치기 위해 invalidate()를 호출할 필요가 없다.
            animate()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}