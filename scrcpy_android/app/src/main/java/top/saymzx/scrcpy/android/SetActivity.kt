package top.saymzx.scrcpy.android

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.documentfile.provider.DocumentFile
import dev.mobile.dadb.AdbKeyPair
import org.json.JSONArray
import top.saymzx.scrcpy.android.entity.Device
import top.saymzx.scrcpy.android.entity.defaultAudioCodec
import top.saymzx.scrcpy.android.entity.defaultFps
import top.saymzx.scrcpy.android.entity.defaultFull
import top.saymzx.scrcpy.android.entity.defaultMaxSize
import top.saymzx.scrcpy.android.entity.defaultSetResolution
import top.saymzx.scrcpy.android.entity.defaultVideoBit
import top.saymzx.scrcpy.android.entity.defaultVideoCodec
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class SetActivity : Activity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_set)
    appData.publicTools.setStatusAndNavBar(this)
    // 设置默认值
    setValue()
    // 设置返回按钮监听
    setBackButtonListener()
    // 设置默认设置监听
    setDefaultVideoCodecListener()
    setDefaultAudioCodecListener()
    setDefaultMaxSizeListener()
    setDefaultFpsListener()
    setDefaultVideoBitListener()
    setDefaultFullListener()
    setDefaultSetResolutionListener()
    // 其他
    setClearDefultListener()
    setRenewKeyListener()
    // 备份恢复
    setKeyExportListener()
    setKeyImportListener()
    setJsonExportListener()
    setJsonImportListener()
    // 关于
    setIndexListener()
    setPrivacyListener()
  }

  // 设置默认值
  private fun setValue() {
    findViewById<Spinner>(R.id.set_default_videoCodec).setSelection(
      appData.publicTools.getStringIndex(
        defaultVideoCodec, resources.getStringArray(R.array.videoCodecItems)
      )
    )
    findViewById<Spinner>(R.id.set_default_audioCodec).setSelection(
      appData.publicTools.getStringIndex(
        defaultAudioCodec, resources.getStringArray(R.array.audioCodecItems)
      )
    )
    findViewById<Spinner>(R.id.set_default_max_size).setSelection(
      appData.publicTools.getStringIndex(
        defaultMaxSize.toString(), resources.getStringArray(R.array.maxSizeItems)
      )
    )
    findViewById<Spinner>(R.id.set_default_fps).setSelection(
      appData.publicTools.getStringIndex(
        defaultFps.toString(), resources.getStringArray(R.array.fpsItems)
      )
    )
    findViewById<Spinner>(R.id.set_default_video_bit).setSelection(
      appData.publicTools.getStringIndex(
        defaultVideoBit.toString(), resources.getStringArray(R.array.videoBitItems1)
      )
    )
    findViewById<Switch>(R.id.set_default_set_resolution).isChecked = defaultSetResolution
    findViewById<Switch>(R.id.set_default_default_full).isChecked = defaultFull
  }

  // 设置返回按钮监听
  private fun setBackButtonListener() {
    findViewById<ImageView>(R.id.set_back).setOnClickListener {
      finish()
    }
  }

  // 设置视频编解码器监听
  private fun setDefaultVideoCodecListener() {
    val view = findViewById<Spinner>(R.id.set_default_videoCodec)
    view.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
      override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val item = view.selectedItem.toString()
        defaultVideoCodec = item
        appData.settings.edit().apply {
          putString("defaultVideoCodec", item)
          apply()
        }
      }

      override fun onNothingSelected(p0: AdapterView<*>?) {
      }

    }
  }

  // 设置音频编解码器监听
  private fun setDefaultAudioCodecListener() {
    val view = findViewById<Spinner>(R.id.set_default_audioCodec)
    view.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
      override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val item = view.selectedItem.toString()
        defaultAudioCodec = item
        appData.settings.edit().apply {
          putString("defaultAudioCodec", item)
          apply()
        }
      }

      override fun onNothingSelected(p0: AdapterView<*>?) {
      }

    }
  }

  // 设置最大大小监听
  private fun setDefaultMaxSizeListener() {
    val view = findViewById<Spinner>(R.id.set_default_max_size)
    view.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
      override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val item = view.selectedItem.toString()
        defaultMaxSize = item.toInt()
        appData.settings.edit().apply {
          putString("defaultMaxSize", item)
          apply()
        }
      }

      override fun onNothingSelected(p0: AdapterView<*>?) {
      }

    }
  }


  // 设置帧率监听
  private fun setDefaultFpsListener() {
    val view = findViewById<Spinner>(R.id.set_default_fps)
    view.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
      override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val item = view.selectedItem.toString()
        defaultFps = item.toInt()
        appData.settings.edit().apply {
          putString("defaultFps", item)
          apply()
        }
      }

      override fun onNothingSelected(p0: AdapterView<*>?) {
      }

    }
  }

  // 设置码率监听
  private fun setDefaultVideoBitListener() {
    val view = findViewById<Spinner>(R.id.set_default_video_bit)
    view.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
      override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val item = view.selectedItem.toString()
        defaultVideoBit = item.toInt()
        appData.settings.edit().apply {
          putString("defaultVideoBit", item)
          apply()
        }
      }

      override fun onNothingSelected(p0: AdapterView<*>?) {
      }

    }
  }

  // 设置是否修改分辨率监听
  private fun setDefaultSetResolutionListener() {
    findViewById<Switch>(R.id.set_default_set_resolution).setOnCheckedChangeListener { _, checked ->
      appData.settings.edit().apply {
        defaultSetResolution = checked
        putBoolean("defaultSetResolution", checked)
        apply()
      }
    }
  }

  // 设置是否全屏监听
  private fun setDefaultFullListener() {
    findViewById<Switch>(R.id.set_default_default_full).setOnCheckedChangeListener { _, checked ->
      appData.settings.edit().apply {
        defaultFull = checked
        putBoolean("defaultFull", checked)
        apply()
      }
    }
  }

  // 设置清除默认设备按钮监听
  private fun setClearDefultListener() {
    findViewById<TextView>(R.id.set_clear_defult).setOnClickListener {
      appData.settings.edit().apply {
        putString("DefaultDevice", "")
        apply()
      }
      Toast.makeText(this, "已清除", Toast.LENGTH_SHORT).show()
    }
  }

  // 设置重新生成密钥按钮监听
  private fun setRenewKeyListener() {
    findViewById<TextView>(R.id.set_clear_defult).setOnClickListener {
      AdbKeyPair.generate(appData.privateKey, appData.publicKey)
    }
  }

  // 设置密钥导出按钮监听
  private fun setKeyExportListener() {
    findViewById<TextView>(R.id.set_export_key).setOnClickListener {
      openDirectory(1)
    }
  }

  // 设置密钥导入按钮监听
  private fun setKeyImportListener() {
    findViewById<TextView>(R.id.set_import_key).setOnClickListener {
      openDirectory(2)
    }
  }

  // 设置密钥导出按钮监听
  private fun setJsonExportListener() {
    findViewById<TextView>(R.id.set_export_json).setOnClickListener {
      openDirectory(3)
    }
  }

  // 设置密钥导入按钮监听
  private fun setJsonImportListener() {
    findViewById<TextView>(R.id.set_import_json).setOnClickListener {
      openDirectory(4)
    }
  }

  // 设置隐私政策按钮监听
  private fun setPrivacyListener() {
    findViewById<TextView>(R.id.set_privacy).setOnClickListener {
      try {
        // 防止没有默认浏览器
        val intent = Intent(Intent.ACTION_VIEW)
        intent.addCategory(Intent.CATEGORY_BROWSABLE)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.data = Uri.parse("https://github.com/mingzhixian/scrcpy/blob/master/PRIVACY.md")
        startActivity(intent)
      } catch (_: Exception) {
      }
    }
  }

  // 设置官网按钮监听
  private fun setIndexListener() {
    findViewById<TextView>(R.id.set_index).setOnClickListener {
      try {
        // 防止没有默认浏览器
        val intent = Intent(Intent.ACTION_VIEW)
        intent.addCategory(Intent.CATEGORY_BROWSABLE)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.data = Uri.parse("https://scrcpy.saymzx.top/")
        startActivity(intent)
      } catch (_: Exception) {
      }
    }
  }

  // 检查存储权限
  private fun openDirectory(mode: Int) {
    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
    // 操作类型(1为密钥导出，2为密钥导入，3为Json导出，4为密钥导入)
    startActivityForResult(intent, mode)
    Toast.makeText(this, "请不要选择Download或其他隐私位置", Toast.LENGTH_LONG).show()
  }

  @SuppressLint("Range")
  override fun onActivityResult(
    requestCode: Int, resultCode: Int, resultData: Intent?
  ) {
    if (resultCode == RESULT_OK) {
      resultData?.data?.also { uri ->
        val documentFile = DocumentFile.fromTreeUri(this, uri)
        if (documentFile == null) {
          Toast.makeText(this, "空地址", Toast.LENGTH_SHORT).show()
          return
        }
        when (requestCode) {
          // 密钥导出
          1 -> {
            val privateKeyDoc = documentFile.findFile("scrcpy_private.key")
            val privateKeyUri = privateKeyDoc?.uri ?: documentFile.createFile(
              "scrcpy/key", "scrcpy_private.key"
            )!!.uri
            writeToFile(appData.privateKey, privateKeyUri, 2)
            val publicKeyDoc = documentFile.findFile("scrcpy_public.key")
            val publicKeyUri =
              publicKeyDoc?.uri ?: documentFile.createFile("scrcpy/key", "scrcpy_public.key")!!.uri
            writeToFile(appData.publicKey, publicKeyUri, 2)
          }
          // 密钥导入
          2 -> {
            val privateKeyDoc = documentFile.findFile("scrcpy_private.key")
            val publicKeyDoc = documentFile.findFile("scrcpy_public.key")
            // 检查文件是否存在
            if (privateKeyDoc == null || publicKeyDoc == null) {
              Toast.makeText(this, "文件不存在", Toast.LENGTH_SHORT).show()
              return
            }
            readFile(appData.privateKey, privateKeyDoc.uri, 2)
            readFile(appData.publicKey, publicKeyDoc.uri, 2)
          }
          // Json导出
          3 -> {
            val dataBaseDoc = documentFile.findFile("scrcpy_database.json")
            val dataBaseUri = dataBaseDoc?.uri ?: documentFile.createFile(
              "scrcpt/json", "scrcpy_database.json"
            )!!.uri
            val jsonArray = JSONArray()
            for (i in appData.devices) jsonArray.put(i.toJson())
            writeToFile(jsonArray.toString(), dataBaseUri, 1)
          }
          // Json导入
          4 -> {
            val dataBaseDoc = documentFile.findFile("scrcpy_database.json")
            // 检查文件是否存在
            if (dataBaseDoc == null) {
              Toast.makeText(this, "文件不存在", Toast.LENGTH_SHORT).show()
              return
            }
            val jsonArray = JSONArray(readFile(null, dataBaseDoc.uri, 1))
            for (i in 0 until jsonArray.length()) {
              appData.deviceListAdapter.newDevice(
                Device(jsonArray.getJSONObject(i))
              )
            }
          }
        }
      }
    }
  }

  // 写入文件(mode为1写入string，mode为2传入File)
  private fun writeToFile(data: Any, uri: Uri, mode: Int) {
    try {
      contentResolver.openFileDescriptor(uri, "w")?.use {
        FileOutputStream(it.fileDescriptor).use {
          if (mode == 1) {
            it.write((data as String).toByteArray())
          } else if (mode == 2) {
            val byteArray = ByteArray(512)
            val fileInputStream = (data as File).inputStream()
            while (true) {
              val len = fileInputStream.read(byteArray)
              if (len < 0) break
              it.write(byteArray, 0, len)
            }
          }
        }
      }
    } catch (e: FileNotFoundException) {
      e.printStackTrace()
    } catch (e: IOException) {
      e.printStackTrace()
    }
  }

  // 读取文件(mode为1返回string，mode为2直接写入File)
  private fun readFile(file: File?, uri: Uri, mode: Int): String {
    try {
      contentResolver.openInputStream(uri)?.use {
        if (mode == 1) {
          var str = ""
          val byteArray = ByteArray(512)
          while (true) {
            val len = it.read(byteArray)
            if (len < 0) {
              return str
            }
            str += String(byteArray, 0, len)
          }
        } else if (mode == 2) {
          val byteArray = ByteArray(512)
          val fileOutputStream = file!!.outputStream()
          while (true) {
            val len = it.read(byteArray)
            if (len < 0) return ""
            fileOutputStream.write(byteArray, 0, len)
          }
        }
      }
    } catch (e: FileNotFoundException) {
      e.printStackTrace()
    } catch (e: IOException) {
      e.printStackTrace()
    }
    return ""
  }
}