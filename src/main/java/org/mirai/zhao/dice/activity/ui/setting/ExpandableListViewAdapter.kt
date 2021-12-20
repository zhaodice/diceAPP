package org.mirai.zhao.dice.activity.ui.setting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import org.mirai.zhao.dice.R
import org.mirai.zhao.dice.component.FixedTextView


/**
 * Created by fyf on 2019/3/1.
 * 描述：是二级显示列表的adapter
 */
class ExpandableListViewAdapter(/*private val context: Context, //Model：定义的数据*/
                                private val groups: Array<String>, //注意，字符数组不要写成{{"A1,A2,A3,A4"}, {"B1,B2,B3,B4，B5"}, {"C1,C2,C3,C4"}}
                                private val children: Array<Array<String>>
) : BaseExpandableListAdapter() {

    override fun getGroupCount(): Int {
        return groups.size
    }

    override fun getChildrenCount(i: Int): Int {
        return children[i].size
    }

    override fun getGroup(i: Int): Any {
        return groups[i]
    }

    override fun getChild(i: Int, i1: Int): Any {
        return children[i][i1]
    }

    override fun getGroupId(i: Int): Long {
        return i.toLong()
    }

    override fun getChildId(i: Int, i1: Int): Long {
        return i1.toLong()
    }

    //分组和子选项是否持有稳定的ID, 就是说底层数据的改变会不会影响到它们
    override fun hasStableIds(): Boolean {
        return true
    }

    /**
     *
     * 获取显示指定组的视图对象
     *
     * @param groupPosition 组位置
     * @param isExpanded 该组是展开状态还是伸缩状态，true=展开
     * @param convertView 重用已有的视图对象
     * @param parent 返回的视图对象始终依附于的视图组
     */
    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup): View? {
        var newConvertView = convertView
        val groupViewHolder: GroupViewHolder
        if (newConvertView == null) {
            newConvertView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_settings_parent, parent, false)
            groupViewHolder = GroupViewHolder()
            groupViewHolder.parentTextViewId = newConvertView.findViewById(R.id.parent_textview_id)
            groupViewHolder.parentImage = newConvertView.findViewById(R.id.parent_image)
            newConvertView.tag = groupViewHolder
        } else {
            groupViewHolder = newConvertView.tag as GroupViewHolder
        }
        groupViewHolder.parentTextViewId.text = groups[groupPosition]
        //如果是展开状态，
        if (isExpanded) {
            groupViewHolder.parentImage.visibility = View.INVISIBLE
        } else {
            groupViewHolder.parentImage.visibility = View.VISIBLE
        }
        return newConvertView
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup): View? {
        var newConvertView: View? = convertView
        val childViewHolder: ChildViewHolder
        if (newConvertView == null) {
            newConvertView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_settings_child, parent, false)
            childViewHolder = ChildViewHolder()
            childViewHolder.childrenItem = newConvertView.findViewById(R.id.chidren_item)
            newConvertView.tag = childViewHolder
        } else {
            childViewHolder = newConvertView.tag as ChildViewHolder
        }
        childViewHolder.childrenItem.text = children[groupPosition][childPosition]
        return newConvertView
    }

    //指定位置上的子元素是否可选中
    override fun isChildSelectable(i: Int, i1: Int): Boolean {
        return true
    }

    internal class GroupViewHolder {
        lateinit var parentTextViewId: FixedTextView
        lateinit var parentImage: ImageView
    }

    internal class ChildViewHolder {
        lateinit var childrenItem: FixedTextView
    }

}
