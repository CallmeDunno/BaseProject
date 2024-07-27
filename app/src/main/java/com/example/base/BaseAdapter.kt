package com.example.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

abstract class BaseAdapter<DataClass, VB: ViewDataBinding>(private val diffUtil: BaseDiffUtil<DataClass>) : ListAdapter<DataClass,
    ViewHolder>(AsyncDifferConfig.Builder(diffUtil).build()){
    abstract val layoutID: Int
    inner class VH(val binding: VB) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return VH(DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutID, parent, false))
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is BaseAdapter<*, *>.VH) {
            bindData(holder.itemView.context, holder.binding as VB, position, getItem(position))
        }
    }
    abstract fun bindData(context: Context, binding: VB, position: Int, model: DataClass)
}

abstract class BaseDiffUtil <DataClass> : DiffUtil.ItemCallback<DataClass>() {
    override fun areContentsTheSame(oldItem: DataClass & Any, newItem: DataClass & Any): Boolean {
        return isItemsTheSame(oldItem, newItem)
    }
    override fun areItemsTheSame(oldItem: DataClass & Any, newItem: DataClass & Any): Boolean {
        return isConTentsTheSame(oldItem, newItem)
    }
    abstract fun isItemsTheSame(oldItem: DataClass, newItem: DataClass): Boolean
    abstract fun isConTentsTheSame(oldItem: DataClass, newItem: DataClass): Boolean
}

