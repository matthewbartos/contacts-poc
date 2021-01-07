package com.example.myapplication.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.hasPermission
import com.example.myapplication.requestPermissionWithRationale

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val alphabetAdapter = AlphabetAdapter(requireContext())
        view.findViewById<RecyclerView>(R.id.rvAlphabet).apply {
            adapter = alphabetAdapter
        }

        val contactsAdapter = ContactsAdapter(requireContext())
        viewModel.contactsLiveData.observe(viewLifecycleOwner) {
            contactsAdapter.contacts = it.toMutableList()
        }

        val contactsRV = view.findViewById<RecyclerView>(R.id.rvContacts)
        contactsRV.adapter = contactsAdapter

        alphabetAdapter.clickCallback = { letterToScroll ->
            contactsAdapter.contacts
                .indexOfFirst { it.firstForGivenLetter == letterToScroll }
                .takeUnless { it == -1 }
                ?.let {
                    contactsRV.scrollToPosition(it)
                }
        }

        if (hasPermission(requireContext(), Manifest.permission.READ_CONTACTS)) {
            viewModel.fetchContacts()
        } else {
            activity?.requestPermissionWithRationale(
                Manifest.permission.READ_CONTACTS,
                RESULT_CODE,
                getString(R.string.contact_permission_rationale)
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == RESULT_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            viewModel.fetchContacts()
        }
    }

    companion object {
        private const val RESULT_CODE = 100
        fun newInstance() = MainFragment()
    }

}