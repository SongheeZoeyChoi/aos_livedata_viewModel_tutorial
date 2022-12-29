package com.example.livedata_viewmodel_tutorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.livedata_viewmodel_tutorial.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        private const val TAG = "MainActivity"
    }

    // 나중에 값이 설정될거라고 lateinit으로 설정
    lateinit var myNumberViewModel: MyNumberViewModel
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * viewBinding
         * */
        // 메인 액티비티 -> 액티비티 메인 바인딩
        // 자동으로 완성된 액티비티 메인 바인딩 클래스 인스턴스를 가져왔다.
        binding = ActivityMainBinding.inflate(layoutInflater)
        // 바인딩과 연결
        setContentView(binding.root)
        //        setContentView(R.layout.activity_main) // --> 뷰 바인딩 사용시 해당 내용 대체됨


        /**
         * viewModel 가져오고 옵저빙 하기
         */
        // 뷰모델 프로바이더를 통해 뷰모델 가져오기
        // 라이프사이클을 가지고있는 녀석을 넣어줌. 즉 자기자신(this)
        // 우리가 가져오고 싶은 뷰모델 클래스를 넣어서 뷰모델을 가져오기
        myNumberViewModel = ViewModelProvider(this).get(MyNumberViewModel::class.java)

        //뷰모델이 가지고있는 값의 변경사항을 관찰할 수 있는 라이브 데이터를 옵저빙 한다.
        myNumberViewModel.currentValue.observe(this, Observer {
            Log.d(TAG, "MainActivity - myNumberViewModel - currentValue 라이브 데이터 값 변경 : $it")
            binding.numberTextview.text = it.toString()
        })

        /**
         * OnClickListener연결
         */
        // 리스너 연결
        binding.plusButton.setOnClickListener(this)
        binding.minusButton.setOnClickListener(this)
    }

    // 클릭
    override fun onClick(view: View?) {
        val userInput = binding.userinputEdittext.text.toString().toInt()

        // 뷰모델이 라이브데이터 값을 변경하는 메소드 실행
        when(view) {
            binding.plusButton ->
                myNumberViewModel.updateValue(actionType = ActionType.PLUS, userInput)
            binding.minusButton ->
                myNumberViewModel.updateValue(actionType = ActionType.MINUS, userInput)
        }
    }
}