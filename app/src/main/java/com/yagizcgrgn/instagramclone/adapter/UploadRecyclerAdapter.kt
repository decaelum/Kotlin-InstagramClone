package com.yagizcgrgn.instagramclone.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.Placeholder
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.yagizcgrgn.instagramclone.databinding.RecyclerRowBinding
import com.yagizcgrgn.instagramclone.model.Post

class UploadRecyclerAdapter(val postList: List<Post>) : RecyclerView.Adapter<UploadRecyclerAdapter.UploadHolder>(){
    class UploadHolder(val recyclerRowBinding: RecyclerRowBinding) : RecyclerView.ViewHolder(recyclerRowBinding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UploadHolder {
        val recyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UploadHolder(recyclerRowBinding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: UploadHolder, position: Int) {
        holder.recyclerRowBinding.rwTvUserName.text = postList[position].email
        holder.recyclerRowBinding.rwTvComment.text = postList[position].commet
        Picasso.get().load(postList[position].downloadUrl).into(holder.recyclerRowBinding.ivPostImage)
    }
}