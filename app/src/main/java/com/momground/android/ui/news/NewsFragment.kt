package com.momground.android.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.momground.android.R
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
        adapter0 = NewsAdapter(requireActivity().supportFragmentManager, requireContext(), weeklySpecial())
        setTheme(adapter0, binding.listNews0)
    }
    private fun setTheme1(){
        adapter1 = NewsAdapter(requireActivity().supportFragmentManager, requireContext(), mockData())
        setTheme(adapter1, binding.listNews1)
    }
    private fun setTheme2(){
        adapter2 = NewsAdapter(requireActivity().supportFragmentManager, requireContext(), healthWeek())
        setTheme(adapter2, binding.listNews2)
    }
    private fun setTheme(adapter: NewsAdapter, recyclerView: RecyclerView){
        recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
    }

    private fun weeklySpecial(): List<NewsItem>{
        return mutableListOf(
            NewsItem("임신 초기 (~13주)에 좋은 음식", "초기","내용","","https://t1.daumcdn.net/cfile/tistory/2737E048575FAF3307?original",
                "질문입니다", "selection","1111,11111,2222,333","11111",false),

            NewsItem("임신 중기 (14주~28주)에 좋은 음식", "식단","*태아성장 및 모체 변화\n임신 첫 12주 또는 첫 3개월 동안, 일반적으로 산모는 1~2kg이 증가하나 혹시 입덧이 있으면 체중은 이보다 덜 증가할 것입니다.\n" +
                    "이 체중의 대부분은 태반과 가슴, 자궁, 그리고 여분의 혈액 생성 등으로 인해 나타납니다.","","https://t1.daumcdn.net/cfile/tistory/993A1D335A2627432E?original",
                "질문입니다","selection","1,2,3,4","1",false),

            NewsItem("엽산을 충분히 섭취하고 있나요?", "식단","내용","","https://www.ibabynews.com/news/photo/202106/95723_45378_027.jpg",
                "질문입니다", "selection","1111,11111,2222,333","11111",false)
        )
    }

    private fun healthWeek(): List<NewsItem>{
        return mutableListOf(
            NewsItem("임신 중 약 복용 잘알아봐야해요!", "건강",getString(R.string.desc0),"","https://health.chosun.com/site/data/img_dir/2022/07/01/2022070101686_0.jpg",
                "임신 중 약물 복용에 대해 설명한 내용 중 올바른 것은 무엇인가요?", "selection","임신 사실을 알기 전 복용한 약물은 임신 4주까지 태아에게 특별한 영향을 미치지 않는다.," +
                        "수정 후 3주~9주 사이에 약물을 복용하면 태아에게 아무런 영향을 미치지 않는다.," +
                        "임신 초기 약물 복용은 유산보다는 태아의 기형 가능성이 더 높다.," +
                        "배란기 후 약물 복용은 태아에게 전혀 문제가 되지 않는다.,","0",false),

            NewsItem("출산 후 생리", "건강","*태아성장 및 모체 변화\n임신 첫 12주 또는 첫 3개월 동안, 일반적으로 산모는 1~2kg이 증가하나 혹시 입덧이 있으면 체중은 이보다 덜 증가할 것입니다.\n" +
                    "이 체중의 대부분은 태반과 가슴, 자궁, 그리고 여분의 혈액 생성 등으로 인해 나타납니다.","","https://post-phinf.pstatic.net/MjAyMzA1MThfMTI4/MDAxNjg0NDA2NTc0ODMy.qU75mUr7i2tQ0pOxXFn9nqFV1-ZY2DL2OJjc2pAy-mIg.GsSWyJYX9BaaMX06aoNgX_iSOGcy_DS9KRWEv_qImpYg.JPEG/001.jpg?type=w800_q75",
                "질문입니다","selection","1,2,3,4","1",false),

            NewsItem("태어나기 직전의 우리태아의 모습", "건강","내용","","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS4SzEXttXSqpILBVcQV9Wgr0jnIC-kzKrMvw&usqp=CAU",
                "질문입니다", "selection","1111,11111,2222,333","11111",false)
        )
    }



    private fun mockData(): List<NewsItem> {
        return mutableListOf(
            NewsItem("입덧에 대해 알아보기!", "초기","내용","","https://www.women119.co.kr/img/mobile/sub/rnm_sub08_img1.png",
                "질문입니다", "selection","1111,11111,2222,333","11111",false),

            NewsItem("임신 초기에는 산모에게 어떤 변화가 있을까요?", "식단","*태아성장 및 모체 변화\n임신 첫 12주 또는 첫 3개월 동안, 일반적으로 산모는 1~2kg이 증가하나 혹시 입덧이 있으면 체중은 이보다 덜 증가할 것입니다.\n" +
                    "이 체중의 대부분은 태반과 가슴, 자궁, 그리고 여분의 혈액 생성 등으로 인해 나타납니다.","","https://cdn.imweb.me/upload/S202304264b90b786c5ed7/1f2c8f9d32bb1.png",
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