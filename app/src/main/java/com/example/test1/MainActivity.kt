package com.example.test1

import androidx.appcompat.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar
import android.widget.EditText
import android.widget.Toast
import android.content.SharedPreferences

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 统一使用一个 SharedPreferences 文件
        val sharedPreferences: SharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        var selectedYear = sharedPreferences.getInt("selectedYear", 2021)  // 1是默认值

        val playButton = findViewById<Button>(R.id.play_button)
        val editTextDate = findViewById<EditText>(R.id.editTextDate2)
        val selectYearButton = findViewById<Button>(R.id.selectYearButton)
        val informationButton = findViewById<Button>(R.id.information_button)

        // 获取当前日期
        val calendar = Calendar.getInstance()
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val defaultDate = String.format("%02d%02d", month, day)
        editTextDate.setText(defaultDate)

        // 读取 SharedPreferences 中保存的 lastLaunchDate
        val lastLaunchDate = sharedPreferences.getString("lastLaunchDate", "")
        val currentDate = defaultDate

        // 点击年份选择按钮
        selectYearButton.setOnClickListener {
            selectedYear = if (selectedYear == 2021) 2022 else 2021  // 切换年份
            val yearText = if (selectedYear == 2021) "2021年" else "2020/2022年"
            Toast.makeText(this, "已选择：$yearText", Toast.LENGTH_SHORT).show()
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putInt("selectedYear", selectedYear)
            editor.apply()
        }

        // 在 MainActivity 中传递参数
        playButton.setOnClickListener {
            val dateInput = editTextDate.text.toString()
            val videoDate = if (dateInput.isNotEmpty()) dateInput else defaultDate  // 如果用户没有输入日期则使用默认日期

            val intent = Intent(this, VideoPlayerActivity::class.java)
            intent.putExtra("videoDate", videoDate)  // 将视频日期传递给 VideoPlayerActivity
            intent.putExtra("selectedYear", selectedYear)  // 传递年份（第一年或第二年）
            startActivity(intent)
        }

        // 执行当日第一次启动的操作
        if (lastLaunchDate != currentDate) {
            // 是第一次启动，进行相应处理
            // 在 SharedPreferences 中更新今天的日期
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("lastLaunchDate", currentDate)  // 更新 lastLaunchDate 为当前日期
            editor.apply()

            val intent = Intent(this, VideoPlayerActivity::class.java)
            intent.putExtra("selectedDate", currentDate)  // 传递当前日期
            intent.putExtra("videoDate", currentDate)  // 传递当前日期作为 videoDate

            startActivity(intent)
            // 比如全屏播放视频
        }

        //装饰

        // 设置信息按钮的点击事件
        informationButton.setOnClickListener {
            // 创建一个弹出窗口（AlertDialog）
            val builder = AlertDialog.Builder(this)
            builder.setTitle("ATTENTION！！")  // 设置标题
            builder.setMessage("1. 输入日期格式：MMDD。\n" +
                    "2.每日首次进入APP播放当日对应视频\n" +
                    "3.2021年缺的视频：\n" +
                    "2021 0112\n2021 0120\n2021 0122\n2021 0123\n\n2021 0402\n\n2021 0512\n\n2021 0604\n2021 0614\n2021 0615\n2021 0616\n2021 0617\n2021 0618\n2021 0619\n2021 0620\n\n2021 0707\n2021 0713\n2021 0721\n\n2021 0806\n2021 0813\n2021 0819\n2021 0829\n2021 0830\n\n2021 0903\n2021 0906\n2021 0913\n2021 0918\n2021 0919\n2021 0925\n2021 0926\n2021 0930\n\n2021 1011\n2021 1022\n\n2021 1108\n2021 1111\n2021 1113\n2021 1125\n2021 1126\n\n2021 1213\n2021 1215\n2021 1216\n" +
                    "4.显示无法播放视频就是没有当日的对应视频(上面列出)，每日早安并非每日。\n" +
                    "5.感谢b站专栏 https://www.bilibili.com/opus/632306850631516161 提供参考。\n")  // 设置信息内容
            builder.setPositiveButton("花Q！") { dialog, _ ->
                dialog.dismiss()  // 点击确定后关闭对话框
            }
            // 显示弹出窗口
            builder.show()
        }

    }

}
