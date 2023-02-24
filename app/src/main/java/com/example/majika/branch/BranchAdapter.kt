package com.example.majika.branch

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.majika.backend.BranchData
import com.example.majika.databinding.BranchItemBinding

class BranchAdapter() :
    RecyclerView.Adapter<BranchAdapter.BranchViewHolder>() {

    private var listBranch = emptyList<BranchData>()

    class BranchViewHolder(private val binding: BranchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(branchData: BranchData) {
                binding.branchName.text = branchData.name
                binding.branchAddress.text = branchData.address
                binding.branchContactPerson.text = "Contact Person : " + branchData.contact_person
                binding.branchPhone.text = "Phone Number : "+ branchData.phone_number
                binding.mapsButton.setOnClickListener{
                    val latitude = branchData.latitude
                    val longitude = branchData.longitude
                    val gmmIntenUri = Uri.parse("geo:${latitude},${longitude}")
                    val mapIntent = Intent(Intent.ACTION_VIEW,gmmIntenUri)
                    mapIntent.setPackage("com.google.android.apps.maps")
                    binding.mapsButton.context.startActivity(mapIntent)
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BranchViewHolder {
        val binding = BranchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BranchViewHolder(binding)
    }

    override fun getItemCount() = listBranch.size

    override fun onBindViewHolder(holder: BranchViewHolder, position: Int) {
            val data = listBranch[position]
            holder.bind(data)
    }

    fun setRestaurants(data: List<BranchData>) {
        this.listBranch = data
        notifyDataSetChanged()
    }
}
