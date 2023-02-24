package com.example.majika.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.majika.MainActivity
import com.example.majika.backend.MenuData
import com.example.majika.databinding.MenuItemBinding
import com.example.majika.room.AppDatabase
import com.example.majika.room.CartItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MenuAdapter() :
    RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    private var listMenu = emptyList<MenuData>()

    class MenuViewHolder(private val binding: MenuItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var appDb : AppDatabase
        fun bind(menuData: MenuData) {
            binding.menuName.text = menuData.name
            (menuData.currency.toString() + " " + menuData.price.toString()).also { binding.menuPrice.text = it }
            binding.menuSold.text = menuData.sold.toString()
            binding.menuDesc.text = menuData.description
            binding.addtocartButton.setOnClickListener{
                appDb = AppDatabase.getDatabase(binding.addtocartButton.context)
                val itemName = menuData.name
                val price = menuData.price
                val total = 1

                if (itemName != null) {
                    val cartItem = CartItem(itemName, price, total)

                    GlobalScope.launch(Dispatchers.IO) {
                        appDb.itemDao().insertItems(cartItem)
                    }
                }
                binding.addMore.visibility = View.VISIBLE
                binding.addtocartButton.visibility = View.GONE
            }
            binding.minButton.setOnClickListener{
                appDb = AppDatabase.getDatabase(binding.minButton.context)
                val itemName = menuData.name
                val price = menuData.price
                val total = Integer.parseInt(binding.itemCount.text.toString()) - 1

                if (total > 0) {
                    binding.itemCount.text = total.toString()

                    if (itemName != null) {
                        val cartItem = CartItem(itemName, price, total)

                        GlobalScope.launch(Dispatchers.IO) {
                            appDb.itemDao().updateItems(cartItem)
                        }
                    }
                } else {
                    if (itemName != null) {
                        val cartItem = CartItem(itemName, price, 0)

                        GlobalScope.launch(Dispatchers.IO) {
                            appDb.itemDao().deleteItems(cartItem)
                        }
                    }
                    binding.addMore.visibility = View.GONE
                    binding.addtocartButton.visibility = View.VISIBLE
                }
            }
            binding.addButton.setOnClickListener{
                appDb = AppDatabase.getDatabase(binding.minButton.context)
                val itemName = menuData.name
                val price = menuData.price
                val total = Integer.parseInt(binding.itemCount.text.toString()) + 1
                binding.itemCount.text = total.toString()

                if (itemName != null) {
                    val cartItem = CartItem(itemName, price, total)

                    GlobalScope.launch(Dispatchers.IO) {
                        appDb.itemDao().updateItems(cartItem)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        val appDb = AppDatabase.getDatabase(MenuFragment)
        return MenuViewHolder(binding)
    }

    override fun getItemCount() = listMenu.size

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val data = listMenu[position]
        holder.bind(data)
    }

    fun setMenus(data: List<MenuData>) {
        this.listMenu = data
        notifyDataSetChanged()
    }
}