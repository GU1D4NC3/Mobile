package com.momground.android.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.momground.android.data.NewsItem
import com.momground.android.databinding.FragmentNewsBinding
import com.momground.android.network.MyClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NewsFragment : Fragment() {
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter0: NewsAdapter
    private lateinit var adapter1: NewsAdapter
    private lateinit var adapter2: NewsAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val newsViewModel =
            ViewModelProvider(this)[NewsViewModel::class.java]

        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setTheme0()
        setTheme1()
        setTheme2()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        /*
            GlobalScope.launch(Dispatchers.IO) {
                val newsItem = NetworkRepository().requestKtorIo3(1)
                Log.d("NEWS", newsItem.toString())
            }

         */

        GlobalScope.launch(Dispatchers.IO) {
            val cats = MyClass().getCatFromApi()
            println(cats.joinToString(separator = "\n") { it.toString() })
        }

        GlobalScope.launch(Dispatchers.IO) {
            val cats = MyClass().getNewsById(1)
            println(cats.data.newsTitle)

        }
         /*

        GlobalScope.launch(Dispatchers.IO) {
            getNews()
        }

          */


    }


    private fun setTheme0(){
        adapter0 = NewsAdapter(requireActivity().supportFragmentManager, requireContext(), mockData())
        setTheme(adapter0, binding.listNews0)
    }
    private fun setTheme1(){
        adapter1 = NewsAdapter(requireActivity().supportFragmentManager, requireContext(), mockData())
        setTheme(adapter1, binding.listNews1)
    }
    private fun setTheme2(){
        adapter2 = NewsAdapter(requireActivity().supportFragmentManager, requireContext(), mockData())
        setTheme(adapter2, binding.listNews2)
    }
    private fun setTheme(adapter: NewsAdapter, recyclerView: RecyclerView){
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}