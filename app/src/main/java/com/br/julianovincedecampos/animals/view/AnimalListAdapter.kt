package com.br.julianovincedecampos.animals.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.br.julianovincedecampos.animals.R
import com.br.julianovincedecampos.animals.databinding.ItemAnimalBinding
import com.br.julianovincedecampos.animals.databinding.ItemAnimalBindingImpl
import com.br.julianovincedecampos.animals.model.Animal
import com.br.julianovincedecampos.animals.util.getProgressDrawable
import com.br.julianovincedecampos.animals.util.loadImage
import kotlinx.android.synthetic.main.item_animal.view.*

class AnimalListAdapter(private val animalList: ArrayList<Animal>) :
    RecyclerView.Adapter<AnimalListAdapter.AnimalViewHolder>(), AnimalClickListener {

    class AnimalViewHolder(var view: ItemAnimalBinding) : RecyclerView.ViewHolder(view.root)

    fun updateAnimalList(newAnimalList: List<Animal>) {
        animalList.clear()
        animalList.addAll(newAnimalList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemAnimalBinding>(
            inflater,
            R.layout.item_animal,
            parent,
            false
        )
        return AnimalViewHolder(view)
    }

    override fun getItemCount() = animalList.size

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        holder.view.animal = animalList[position]
        holder.view.listener = this
    }

    override fun onClick(view: View) {
        for (animal in animalList) {
            if (view.tag == animal.name) {
                val action = ListFragmetFragmentDirections.ActionGoToDetail(animal)
                Navigation.findNavController(view).navigate(action)
            }
        }
    }
}