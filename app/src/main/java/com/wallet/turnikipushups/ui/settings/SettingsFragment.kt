package com.wallet.turnikipushups.ui.settings

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import com.wallet.turnikipushups.R
import com.wallet.turnikipushups.activities.MainActivity
import com.wallet.turnikipushups.databinding.FragmentSettingsBinding
import com.wallet.turnikipushups.db.AppSharedPreferense
import com.wallet.turnikipushups.di.ViewModelFactory
import com.wallet.turnikipushups.ui.BaseFragment
import com.wallet.turnikipushups.ui.PopUps
import de.raphaelebner.roomdatabasebackup.core.RoomBackup


class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {
    override fun bind(inflater: LayoutInflater): FragmentSettingsBinding {
        return FragmentSettingsBinding.inflate(inflater)
    }

    private val viewModel: SettingsViewModel by viewModels {
        ViewModelFactory{
            getAppComponent(requireContext()).settingsViewModel()
        }
    }



    override fun initView() {
        withBinding {
            verifyStoragePermissions(requireActivity())
            toolbar.setOnClickListener {
                findNavController().popBackStack()
            }
            saitL.setOnClickListener {
                startActivity(
                    Intent(
                        "android.intent.action.VIEW",
                        Uri.parse("https://xn--h1aafkolh.xn--j1amh/ua")
                    )
                )
            }
            notificationL.setOnClickListener {
                findNavController().navigate(R.id.action_settingsFragment_to_notificationFragment)
            }
            shareL.setOnClickListener {
                val intent= Intent()
                intent.action=Intent.ACTION_SEND
                intent.putExtra(Intent.EXTRA_TEXT,"Hey Check out this Great app:")
                intent.type="text/plain"
                startActivity(Intent.createChooser(intent,"Share To:"))
            }
            backUpL.setOnClickListener {
                if(hasStoragePermissions(requireActivity())){
                    backUpDatabases()
                }else{
                    verifyStoragePermissions(requireActivity())
                }
            }
            restoreL.setOnClickListener {
                if(hasStoragePermissions(requireActivity())){
                    restoreDatabase()
                }else{
                    verifyStoragePermissions(requireActivity())
                }
            }
            languageL.setOnClickListener {
                PopUps().languagePicker(requireActivity()){
                    AppSharedPreferense().getInstance(requireContext()).setUserLanguage(it)
                    findNavController().popBackStack()
                }
            }
            deleteL.setOnClickListener {
                viewModel.appDatabase.getStatPushUpsDao().deleteAll()
                viewModel.appSharedPrefer.setLvlTrain(0)
                Toast.makeText(requireContext(),"delete successful",Toast.LENGTH_SHORT).show()
            }
            emailL.setOnClickListener {
                sendEmail()
            }
        }
    }

    private fun sendEmail() {
        val TO = arrayOf("info@powerpullup.pro")
        val CC = arrayOf("")
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.data = Uri.parse("mailto:")
        emailIntent.type = "text/plain"
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO)
        emailIntent.putExtra(Intent.EXTRA_CC, CC)
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here")
        startActivity(Intent.createChooser(emailIntent, "Send mail..."))
    }

    private val REQUEST_EXTERNAL_STORAGE = 4124
    private val PERMISSIONS_STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )


    private fun hasStoragePermissions(activity: Activity):Boolean{
        val permission = ActivityCompat.checkSelfPermission(
            activity,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        return permission == PackageManager.PERMISSION_GRANTED
    }

    private fun verifyStoragePermissions(activity: Activity) {
        if (!hasStoragePermissions(activity)) {
            ActivityCompat.requestPermissions(
                activity,
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE
            )
        }
    }

    private fun backUpDatabases(){
        (requireActivity() as MainActivity).backup
            .database(viewModel.appDatabase)
            .enableLogDebug(true)
            .backupLocation(RoomBackup.BACKUP_FILE_LOCATION_CUSTOM_DIALOG)
            .apply {
                onCompleteListener { success, message, exitCode ->
                    Toast.makeText(requireContext(),"$message",Toast.LENGTH_SHORT).show()
                }
            }
            .backup()
    }
    private fun restoreDatabase(){
        (requireActivity() as MainActivity).backup
            .database(viewModel.appDatabase)
            .enableLogDebug(true)
            .backupLocation(RoomBackup.BACKUP_FILE_LOCATION_CUSTOM_DIALOG)
            .apply {
                onCompleteListener { success, message, exitCode ->
                    Log.d("mylog", "success: $success, message: $message, exitCode: $exitCode")
                    if (success) restartApp(Intent(requireActivity(), MainActivity::class.java))

                }
            }
            .restore()
    }

}