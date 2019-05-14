package com.example.kotlinapplication

import android.content.Context
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup

import java.util.ArrayList
import java.util.concurrent.atomic.AtomicInteger


abstract class RecyclerViewAdapter(var context: Context?, enableSelectedMode: Boolean) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listWrapperModels: ArrayList<ModelWrapper>? = null
    private var listWrapperModelsBackup: ArrayList<ModelWrapper>? = null

    val inflater: LayoutInflater
    private val onItemClickListeners: ArrayList<OnItemClickListener>
    private var onItemTouchChangeListener: OnItemTouchChangedListener? = null
    private var onItemSelectionChangeListener: OnItemSelectionChangedListener? = null
    var isInSelectedMode: Boolean = false
        private set
    var recyclerView: RecyclerView? = null
        private set

    init {
        this.inflater = LayoutInflater.from(context)
        this.listWrapperModels = ArrayList()
        this.onItemClickListeners = ArrayList(1)

        setSelectedMode(enableSelectedMode)
    }

    protected fun initDiffUtilCallback(oldItems: ArrayList<ModelWrapper>, newItems: ArrayList<ModelWrapper>): DiffUtilCallBack {
        return DiffUtilCallBack(oldItems, newItems)
    }

    fun backup() {
        listWrapperModelsBackup = ArrayList(listWrapperModels!!.size)
        try {
            for (modelWrapper in listWrapperModels!!) {
                listWrapperModelsBackup!!.add(modelWrapper.clone())
            }
        } catch (e: CloneNotSupportedException) {
            e.printStackTrace()
        }

    }

    fun recoverBackup() {
        this.listWrapperModels = listWrapperModelsBackup
        notifyItemRangeChanged(0, itemCount)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = null
        this.context = null
    }

    fun addOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListeners.add(onItemClickListener)
    }

    private fun notifyItemClickListener(
        adapter: RecyclerView.Adapter<*>,
        viewHolder: RecyclerView.ViewHolder?,
        viewType: Int,
        position: Int
    ) {
        for (onItemClickListener in onItemClickListeners) {
            onItemClickListener.onItemClick(adapter, viewHolder, viewType, position)
        }
    }

    fun removeOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListeners.remove(onItemClickListener)
    }

    fun setOnItemTouchChangeListener(onItemTouchChangeListener: OnItemTouchChangedListener) {
        this.onItemTouchChangeListener = onItemTouchChangeListener
    }

    fun setOnItemSelectionChangeListener(onItemSelectionChangeListener: OnItemSelectionChangedListener) {
        this.onItemSelectionChangeListener = onItemSelectionChangeListener
    }

    fun clear() {
        val itemCount = itemCount
        listWrapperModels!!.clear()
        notifyItemRangeRemoved(0, itemCount)
    }

    fun  refresh(models: List<Any>) {
        val itemCount = itemCount
        listWrapperModels!!.clear()
        notifyItemRangeRemoved(0, itemCount)
        addModels(models, false)
    }

    fun  addModels(listModels: List<Any>, isScroll: Boolean) {
        addModels(listModels, VIEW_TYPE_NORMAL, isScroll)
    }

    fun addModels(listModels: List<Any>, viewType: Int, isScroll: Boolean) {
        addModels(listModels, 0, listModels.size - 1, viewType, isScroll)
    }

    fun addModels(listModels: List<Any>, fromIndex: Int, toIndex: Int, viewType: Int, isScroll: Boolean) {
        val startInsertedPosition = itemCount
        val endInsertedPosition = startInsertedPosition + listModels.size
        for (i in fromIndex..toIndex) {
            addModel(listModels[i], viewType, false, false)
        }
        notifyItemRangeInserted(startInsertedPosition, endInsertedPosition)
        if (isScroll) {
            recyclerView!!.scrollToPosition(listWrapperModels!!.size - 1)
        }
    }

    fun addModel(model: Any?, isScroll: Boolean) {
        addModel(listWrapperModels!!.size, model, isScroll)
    }

    fun addModel(model: Any?, isScroll: Boolean, isUpdate: Boolean) {
        addModel(listWrapperModels!!.size, model, isScroll, isUpdate)
    }

    fun addModel(model: Any?, viewType: Int, isScroll: Boolean) {
        addModel(listWrapperModels!!.size, model, viewType, isScroll)
    }

    fun addModel(model: Any?, viewType: Int, isScroll: Boolean, isUpdate: Boolean) {
        addModel(listWrapperModels!!.size, model, viewType, isScroll, isUpdate)
    }

    fun addModel(index: Int, model: Any?, isScroll: Boolean) {
        addModel(index, model, VIEW_TYPE_NORMAL, isScroll, true)
    }

    fun addModel(index: Int, model: Any?, isScroll: Boolean, isUpdate: Boolean) {
        addModel(index, model, VIEW_TYPE_NORMAL, isScroll, isUpdate)
    }

    fun updateModel(position: Int, model: Any, isScroll: Boolean) {
        getListWrapperModels()!![position].model = model
        notifyItemChanged(position)
        if (isScroll) {
            recyclerView!!.scrollToPosition(position)
        }
    }

    @JvmOverloads
    fun addModel(index: Int, model: Any?, viewType: Int, isScroll: Boolean, isUpdate: Boolean = true) {
        val modelWrapper = ModelWrapper(model, viewType)
        this.listWrapperModels!!.add(index, modelWrapper)
        if (isUpdate) {
            notifyItemInserted(index)
        }
        if (isScroll) {
            recyclerView!!.scrollToPosition(index)
        }
    }

    fun removeModel(index: Int) {
        if (index >= 0) {
            this.removeModel(index, true)
        }
    }

    fun removeModel(index: Int, isUpdate: Boolean) {
        this.listWrapperModels!!.removeAt(index)
        if (isUpdate) {
            notifyItemRemoved(index)
        }
    }

    fun setSelectedMode(isSelected: Boolean) {
        if (this.isInSelectedMode && !isSelected) {
            deSelectAllItems(null)
            notifyItemRangeChanged(0, itemCount)
        }
        isInSelectedMode = isSelected
    }

    fun setSelectedItem(position: Int, isSelected: Boolean) {
        if (isInSelectedMode && position >= 0 && position < listWrapperModels!!.size) {
            val modelWrapper = listWrapperModels!![position]
            if (modelWrapper.isSelected != isSelected) {
                listWrapperModels!![position].isSelected = isSelected
                notifyItemChanged(position)
            }
        }
    }

    fun deSelectAllItems(onEachUnSelectedItem: OnEachUnSelectedItem?) {
        val size = listWrapperModels!!.size
        for (i in 0 until size) {
            val modelWrapper = listWrapperModels!![i]
            if (onEachUnSelectedItem != null && !modelWrapper.isSelected) {
                onEachUnSelectedItem.onEachUnselectedItem(modelWrapper)
            }
            modelWrapper.isSelected = false
        }
    }

    interface OnEachUnSelectedItem {
        fun onEachUnselectedItem(modelWrapper: ModelWrapper)
    }

    fun removeAllSelectedItems() {
        if (isInSelectedMode) {
            val listItemLeft = ArrayList<ModelWrapper>()
            deSelectAllItems(object : OnEachUnSelectedItem {
                override fun onEachUnselectedItem(modelWrapper: ModelWrapper) {
                    listItemLeft.add(modelWrapper)
                }
            })

            val diffResult = DiffUtil
                .calculateDiff(initDiffUtilCallback(listWrapperModels!!, listItemLeft))

            listWrapperModels = listItemLeft
            diffResult.dispatchUpdatesTo(this)
        }
    }

    fun isItemSelected(position: Int): Boolean {
        return isInSelectedMode && position >= 0 &&
                position < listWrapperModels!!.size &&
                listWrapperModels!![position].isSelected
    }

    fun <T> getSelectedItemModel(type: Class<T>): List<T> {
        val result = ArrayList<T>()
        for (modelWrapper in listWrapperModels!!) {
            val model = modelWrapper.model
            if (modelWrapper.isSelected && model != null) {
                if (model.javaClass == type) {
                    result.add(type.cast(modelWrapper.model))
                }
            }
        }
        return result
    }

    fun <T> forEachModels(type: Class<T>, onEachModel: OnEachModel<T>) {
        for (modelWrapper in listWrapperModels!!) {
            val model = modelWrapper.model
            if (model != null && model.javaClass == type) {
                onEachModel.onEachModel(type.cast(model))
            }
        }
    }

    fun forEachItem(onEachItem: OnEachItem) {
        for (modelWrapper in listWrapperModels!!) {
            onEachItem.onEachItem(modelWrapper)
        }
    }

    interface OnEachItem {
        fun onEachItem(modelWrapper: ModelWrapper)
    }

    interface OnEachModel<T> {
        fun onEachModel(model: T?)
    }

    fun <T> getItem(position: Int, classType: Class<T>): T? {
        return classType.cast(listWrapperModels!![position].model)
    }

    override fun getItemViewType(position: Int): Int {
        return listWrapperModels!![position].viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder = solvedOnCreateViewHolder(parent, viewType)
        setClickStateBackground(viewHolder!!.itemView, viewType, false)
        viewHolder.itemView.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (onItemTouchChangeListener != null) {
                        onItemTouchChangeListener!!.onItemPress(viewHolder, viewType)
                    }
                }

                MotionEvent.ACTION_CANCEL -> {
                    if (onItemTouchChangeListener != null) {
                        onItemTouchChangeListener!!.onItemRelease(viewHolder, viewType)
                    }
                }

                MotionEvent.ACTION_UP -> {
                    val itemPosition = getItemPosition(view)
                    setClickStateBackground(view, viewType, false)
                    notifyItemClickListener(this@RecyclerViewAdapter, viewHolder, viewType, itemPosition)
                }

                else -> {
                }
            }
            false
        }
        return viewHolder
    }

    private fun getItemPosition(view: View): Int {
        return recyclerView!!.getChildLayoutPosition(view)
    }

    protected open fun solvedOnCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        return if (viewType == VIEW_TYPE_NORMAL) {
            initNormalViewHolder(parent)
        } else null
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val modelWrapper = listWrapperModels!![position]
        val viewType = modelWrapper.viewType
        if (onItemSelectionChangeListener != null) {
            onItemSelectionChangeListener!!.onItemSelectionChanged(holder, viewType, modelWrapper.isSelected)
        }
        solvedOnBindViewHolder(holder, viewType, position)
    }

    protected open fun solvedOnBindViewHolder(viewHolder: RecyclerView.ViewHolder, viewType: Int, position: Int) {
        if (viewType == VIEW_TYPE_NORMAL) {
            bindNormalViewHolder(viewHolder as NormalViewHolder, position)
        }
    }

    protected abstract fun initNormalViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

    protected abstract fun bindNormalViewHolder(holder: NormalViewHolder, position: Int)

    protected fun setClickStateBackground(view: View, viewType: Int, isPress: Boolean) {

    }

    fun getListWrapperModels(): List<ModelWrapper>? {
        return listWrapperModels
    }

    fun setListWrapperModels(listWrapperModels: ArrayList<ModelWrapper>) {
        this.listWrapperModels = listWrapperModels
    }

    fun clearListSelectedItems() {
        for (modelWrapper in listWrapperModels!!) {
            modelWrapper.isSelected = false
        }
    }

    override fun getItemCount(): Int {
        return listWrapperModels!!.size
    }

    interface OnItemClickListener {
        fun onItemClick(
            adapter: RecyclerView.Adapter<*>,
            viewHolder: RecyclerView.ViewHolder?,
            viewType: Int,
            position: Int
        )
    }

    interface OnItemSelectionChangedListener {
        fun onItemSelectionChanged(viewHolder: RecyclerView.ViewHolder, viewType: Int, isSelected: Boolean)
    }

    interface OnItemTouchChangedListener {
        fun onItemPress(viewHolder: RecyclerView.ViewHolder?, viewType: Int)

        fun onItemRelease(viewHolder: RecyclerView.ViewHolder?, viewType: Int)
    }

    open class NormalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class ModelWrapper(model: Any?, internal var viewType: Int) : Cloneable {
        internal var id = idGenerator.getAndIncrement()
        var model: Any? = null
            internal set
        var isSelected = false
            internal set

        init {
            this.model = model
        }

        override fun equals(obj: Any?): Boolean {
            if (obj !is ModelWrapper) {
                return false
            }
            val modelWrapper = obj as ModelWrapper?
            if (modelWrapper!!.viewType != this.viewType) {
                return false
            }

            if (modelWrapper.isSelected != this.isSelected) {
                return false
            }

            return if (modelWrapper.model == null) {
                this.model == null
            } else {
                modelWrapper.model == this.model
            }
        }

        @Throws(CloneNotSupportedException::class)
        public override fun clone(): ModelWrapper {
            return super.clone() as ModelWrapper
        }
    }

    companion object {
        var TAG = "RecyclerViewAdapter"
        val VIEW_TYPE_NORMAL = 0

        var idGenerator = AtomicInteger()
    }
}