package com.example.test1


import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class VideoPlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)

        // 获取传递的参数
        val videoDate = intent.getStringExtra("videoDate") ?: "1010"  // 默认日期
        val selectedYear = intent.getIntExtra("selectedYear", 2021)  // 默认选择第一年

        // 设置显示的日期
        val textViewDate = findViewById<TextView>(R.id.textViewDate)
        textViewDate.text = "播放日期：$selectedYear 年 $videoDate"

        // 根据日期和年份选择视频
        val videoUri = getVideoUri(videoDate, selectedYear)

        // 设置 VideoView 播放视频
        val videoView = findViewById<VideoView>(R.id.videoView)
        videoView.setVideoURI(videoUri)
        videoView.start()

        // 返回按钮
        val backButton = findViewById<Button>(R.id.back)
        backButton.setOnClickListener {
            finish()  // 返回到主界面
        }


    }


    // 根据日期和年份选择视频路径
    private fun getVideoUri(date: String, year: Int): Uri {
        val videoPath = "android.resource://${packageName}/raw/video_${year}$date"
        return Uri.parse(videoPath)
    }
}
