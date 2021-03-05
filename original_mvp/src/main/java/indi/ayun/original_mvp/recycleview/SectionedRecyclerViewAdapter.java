package indi.ayun.original_mvp.recycleview;

import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import indi.ayun.original_mvp.base.BaseRVAdapter;
import indi.ayun.original_mvp.recycleview.old.ViewHolder;

public abstract class SectionedRecyclerViewAdapter<RH extends ViewHolder,
        H extends ViewHolder, VH extends ViewHolder,
        F extends ViewHolder, FO extends ViewHolder> extends
        BaseRVAdapter {

    //用来标记每个分组的Header
    protected static final int TYPE_SECTION_HEADER = -1;

    //用来标记每个分组的Footer
    protected static final int TYPE_SECTION_FOOTER = -2;

    //用来标记每个分组的内容
    protected static final int TYPE_SECTION_CON = -3;

    //用来标记整个列表的Header
    protected static final int TYPE_HEADER = 0;  //顶部HeaderView

    //用来标记整个列表的Footer
    protected static final int TYPE_FOOTER = 1;  //底部FooterView

    //空布局
    public static final int TYPE_EMPTY = -4;

    //用来保存分组section位置
    private int[] sectionForPosition = null;

    //用来保存分组内的每项的position位置
    private int[] positionWithinSection = null;

    //用来记录每个位置是否是一个组内Header
    private boolean[] isSectionHeader = null;

    //用来记录每个位置是否是一个组内Footer
    private boolean[] isSectionFooter = null;

    //item的总数，注意，是总数，包含所有项
    private int allCount = 0;

    public SectionedRecyclerViewAdapter() {
        super();
        //RecyclerView采用观察者(Observer)模式，对外提供了registerDataSetObserver和unregisterDataSetObserver
        //两个方法，用来监控数据集的变化
        registerAdapterDataObserver(new SectionDataObserver());//主要用于注册与解绑适配器数据的观察者模式
    }
    /**
     * 是否是列表的Header
     *
     * @param viewType
     * @return
     */
    protected boolean isHeaderViewType(int viewType) {
        return viewType == TYPE_HEADER;
    }

    /**
     * 是否是分组header
     *
     * @param viewType
     * @return
     */
    protected boolean isSectionHeaderViewType(int viewType) {
        return viewType == TYPE_SECTION_HEADER;
    }

    /**
     * 是否是分组内容
     *
     * @param viewType
     * @return
     */
    protected boolean isSectionConViewType(int viewType) {
        return viewType == TYPE_SECTION_CON;
    }

    /**
     * 是否是分组footer
     *
     * @param viewType
     * @return
     */
    protected boolean isSectionFooterViewType(int viewType) {
        return viewType == TYPE_SECTION_FOOTER;
    }

    /**
     * 是否是列表的footer
     *
     * @param viewType
     * @return
     */
    protected boolean isFooterViewType(int viewType) {
        return viewType == TYPE_FOOTER;
    }
    /**
     * 是否是为空布局
     *
     * @param viewType
     * @return
     */
    protected boolean isEmptyViewType(int viewType) {
        return viewType == TYPE_EMPTY;
    }
    protected int getHeaderViewType() {
        return TYPE_HEADER;
    }

    protected int getSectionHeaderViewType(int section) {
        return TYPE_SECTION_HEADER;
    }

    protected int getSectionItemViewType(int section, int position) {
        return TYPE_SECTION_CON;
    }

    protected int getSectionFooterViewType(int section) {
        return TYPE_SECTION_FOOTER;
    }

    protected int getFooterViewType() {
        return TYPE_FOOTER;
    }

    /**
     * 对应位置是否是一个分组header
     */
    public boolean isSectionHeaderPosition(int position) {
        if (isSectionHeader == null) {
            setupPosition();
        }
        return isSectionHeader[position];
    }

    /**
     * 对应位置是否是一个分组footer
     */
    public boolean isSectionFooterPosition(int position) {
        if (isSectionFooter == null) {
            setupPosition();
        }
        return isSectionFooter[position];
    }

    /**
     * 返回当前的ViewHolder
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        if (viewType == TYPE_EMPTY) {
            viewHolder = new EmptyViewHolder(emptyView);
        } else {
            if (isSectionHeaderViewType(viewType)) {
                viewHolder = onCreateSectionHeaderViewHolder(parent, viewType);
            } else if (isSectionFooterViewType(viewType)) {
                viewHolder = onCreateSectionFooterViewHolder(parent, viewType);
            } else if (isFooterViewType(viewType)) {
                viewHolder = onCreateFooterViewHolder(parent, viewType);
            } else if (isHeaderViewType(viewType)) {
                viewHolder = onCreateHeaderViewHolder(parent, viewType);
            } else {
                viewHolder = onCreateItemViewHolder(parent, viewType);
            }
        }
        return viewHolder;
    }

    /**
     * 绑定当前的viewHolder
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (emptyViewVisible) {//此时数据集为空，需要设置空布局
        } else {
            setViewHolder(holder, position);
        }
    }

    private void setViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (hasHeader()) {//如果整个列表有header
            if (position == 0) {
                onBindHeaderViewHolder((RH) holder);
            } else if (position + 1 < getItemCount()) {
                final int section = sectionForPosition[position - 1];
                int index = positionWithinSection[position - 1];
                if (isSectionHeaderPosition(position - 1)) {//当前位置是分组header
                    onBindSectionHeaderViewHolder((H) holder, section);
                    ((H) holder).getConvertView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onSectionHeaderClickListener.onSectionHeaderClick(section);
                        }
                    });

                } else if (isSectionFooterPosition(position - 1)) {//当前位置是分组的footer

                    onBindSectionFooterViewHolder((F) holder, section);
                    ((F) holder).getConvertView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (onSectionFooterClickListener != null) {
                                onSectionFooterClickListener.onSectionFooterClick(section);
                            }
                        }
                    });

                } else {//当前位置是组内item
                    onBindItemViewHolder((VH) holder, section, index);

                    ((VH) holder).getConvertView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onItemClickListener.onItemClick(section, position - 1);
                        }
                    });

                    ((VH) holder).getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            if (onItemLongClickListener != null) {
                                onItemLongClickListener.onItemLongClick(section, position - 1);
                            }
                            return true;
                        }
                    });

                }
            } else {//当前位置是整个列表的footer
                onBindFooterViewHolder((FO) holder);
            }
        } else {//整个列表没有Header
            if (position + 1 < getItemCount()) {
                final int section = sectionForPosition[position];
                int index = positionWithinSection[position];
                if (isSectionHeaderPosition(position)) {//当前位置是分组Header
                    onBindSectionHeaderViewHolder((H) holder, section);
                    ((H) holder).getConvertView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (onSectionHeaderClickListener != null) {
                                onSectionHeaderClickListener.onSectionHeaderClick(section);
                            }
                        }
                    });

                } else if (isSectionFooterPosition(position)) {//当前位置是分组footer

                    onBindSectionFooterViewHolder((F) holder, section);
                    ((F) holder).getConvertView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (onSectionFooterClickListener != null) {
                                onSectionFooterClickListener.onSectionFooterClick(section);
                            }
                        }
                    });

                } else {//当前位置是分组的item
                    onBindItemViewHolder((VH) holder, section, index);
                    ((VH) holder).getConvertView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (onItemClickListener != null) {
                                onItemClickListener.onItemClick(section, position);
                            }
                        }
                    });

                    ((VH) holder).getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            if (onItemLongClickListener != null) {
                                onItemLongClickListener.onItemLongClick(section, position);
                            }
                            return true;
                        }
                    });

                }
            } else {//当前位置是整个列表的footer
                onBindFooterViewHolder((FO) holder);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (sectionForPosition == null) {
            setupPosition();
        }
        if (emptyViewVisible) {
            return TYPE_EMPTY;
        } else {
            if (hasHeader()) {
                if (position == 0) {
                    return getHeaderViewType();
                } else if (position + 1 < getItemCount()) {
                    int section = sectionForPosition[position - 1];
                    int index = positionWithinSection[position - 1];
                    if (isSectionHeaderPosition(position - 1)) {
                        return getSectionHeaderViewType(section);
                    } else if (isSectionFooterPosition(position - 1)) {
                        return getSectionFooterViewType(section);
                    } else {
                        return getSectionItemViewType(section, index);
                    }
                }
                return getFooterViewType();
            } else {
                if (position + 1 < getItemCount()) {
                    int section = sectionForPosition[position];
                    int index = positionWithinSection[position];
                    if (isSectionHeaderPosition(position)) {
                        return getSectionHeaderViewType(section);
                    } else if (isSectionFooterPosition(position)) {
                        return getSectionFooterViewType(section);
                    } else {
                        return getSectionItemViewType(section, index);
                    }
                }
                return getFooterViewType();
            }
        }
    }
    //=========================================================================================================计算个数和位置
    /**
     * 返回item总数（包含顶部Header、底部Footer、分组hader和分组footer以及分组item内容）
     */
    @Override
    public int getItemCount() {
        if (hasHeader()) {
            return allCount + 2;
        } else {
            return allCount + 1;
        }
    }

    //定义一个内部类，每当数据集合发生改变时，设置控件的位置信息
    class SectionDataObserver extends RecyclerView.AdapterDataObserver {
        @Override
        public void onChanged() {
            setupPosition();
            checkEmpty();//检查数据是否为空，设置空布局
        }
        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            checkEmpty();//检查数据是否为空，设置空布局
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            checkEmpty();//检查数据是否为空，设置空布局
        }
    }
    private void setupPosition() {
        allCount = countItems();//计算出item的总数量
        setupArrays(allCount);//得到item的总数量后，初始化几个数组:初始化与position相对应的section数组，初始化与section相对应的position
        // 的数组，初始化当前位置是否是一个Header的数组，初始化当前位置是否是一个Footer的数组
        calculatePositions();//通过计算每个item的位置信息，将上一步初始化后的数组填充数据，最终这几个数组保存了每个位置的item
        // 的状态信息，即：是否是header，是否是footer，所在的position是多少，所在的section是多少
    }

    /**
     * 计算item的总数量
     *
     * @return
     */
    private int countItems() {
        int count = 0;
        int sections = getSectionCount();

        for (int i = 0; i < sections; i++) {
            count += 1 + getItemCountForSection(i) + (hasSectionFooter(i) ? 1 : 0);
        }
        return count;
    }

    /**
     * 通过item的总数量，初始化几个数组:初始化与position相对应的section数组，
     * 初始化与section相对应的position的数组，初始化当前位置是否是一个Header的数组，
     * 初始化当前位置是否是一个Footer的数组
     *
     * @param count
     */
    private void setupArrays(int count) {
        sectionForPosition = new int[count];
        positionWithinSection = new int[count];
        isSectionHeader = new boolean[count];
        isSectionFooter = new boolean[count];
    }

    /**
     * 通过计算每个item的位置信息，将上一步初始化后的数组填充数据，
     * 最终这几个数组保存了每个位置的item的状态信息，即：是否是header，是否是footer，
     * 所在的position是多少，所在的section是多少
     */
    private void calculatePositions() {
        int sections = getSectionCount();
        int index = 0;

        for (int i = 0; i < sections; i++) {
            setupItems(index, true, false, i, 0);
            index++;

            for (int j = 0; j < getItemCountForSection(i); j++) {
                setupItems(index, false, false, i, j);
                index++;
            }

            if (hasSectionFooter(i)) {
                setupItems(index, false, true, i, 0);
                index++;
            }
        }
    }

    /**
     * 保存每个位置对应的数据信息
     *
     * @param index    从0开始的每个最小单位所在的位置，从0开始，到count结束
     * @param isHeader 所在index位置的item是否是header
     * @param isFooter 所在index位置的item是否是footer
     * @param section  所在index位置的item对应的section
     * @param position 所在index位置的item对应的position
     */
    private void setupItems(int index, boolean isHeader, boolean isFooter, int section, int
            position) {
        this.isSectionHeader[index] = isHeader;
        this.isSectionFooter[index] = isFooter;
        sectionForPosition[index] = section;
        positionWithinSection[index] = position;
    }


    //==============================================================================================================
    /**
     * 整个列表是否有Header
     */
    protected abstract boolean hasHeader();
    /**
     * 返回分组的数量,也就是整个item的数量
     *
     * @return
     */
    protected abstract int getSectionCount();

    /**
     * 返回当前分组的item数量，也就是单个分组item中包含的item数量，
     *
     * @param section 第一个item
     * @return
     */
    protected abstract int getItemCountForSection(int section);

    /**
     * 当前分组是否有footer 也就是单个分组item中是否包含footer
     *
     * @param section
     * @return
     */
    protected abstract boolean hasSectionFooter(int section);

    /**
     * 为整个列表创建一个类型为RH的ViewHolder
     *
     * @param parent
     * @param viewType
     * @return
     */
    protected abstract RH onCreateHeaderViewHolder(ViewGroup parent, int viewType);

    /**
     * 为分组header创建一个类型为H的ViewHolder
     *
     * @param parent
     * @param viewType
     * @return
     */
    protected abstract H onCreateSectionHeaderViewHolder(ViewGroup parent, int viewType);

    /**
     * 为分组内容创建一个类型为VH的ViewHolder
     *
     * @param parent
     * @param viewType
     * @return
     */
    protected abstract VH onCreateItemViewHolder(ViewGroup parent, int viewType);

    /**
     * 为分组footer创建一个类型为F的ViewHolder
     *
     * @param parent
     * @param viewType
     * @return
     */
    protected abstract F onCreateSectionFooterViewHolder(ViewGroup parent, int viewType);


    /**
     * 为整个列表创建一个类型为FO的ViewHolder
     *
     * @param parent
     * @param viewType
     * @return
     */
    protected abstract FO onCreateFooterViewHolder(ViewGroup parent, int viewType);

    /**
     * 绑定Header数据
     *
     * @param holder
     */
    protected abstract void onBindHeaderViewHolder(RH holder);

    /**
     * 绑定分组的Header数据
     *
     * @param holder
     * @param section
     */
    protected abstract void onBindSectionHeaderViewHolder(H holder, int section);

    /**
     * 绑定分组数据
     *
     * @param holder
     * @param section
     * @param position
     */
    protected abstract void onBindItemViewHolder(VH holder, int section, int position);

    /**
     * 绑定分组的footer数据
     *
     * @param holder
     * @param section
     */
    protected abstract void onBindSectionFooterViewHolder(F holder, int section);

    /**
     * 底部布局
     * @param holder
     */
    protected abstract void onBindFooterViewHolder(FO holder);

    //============================================================================================================空布局
    private View emptyView;
    private boolean emptyViewVisible;

    public View getEmptyView() {
        return emptyView;
    }

    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
    }
    private void checkEmpty() {
        if (emptyView != null) {
            if (hasHeader()) {
                emptyViewVisible = getItemCount() == 2;
            } else {
                emptyViewVisible = getItemCount() == 1;
            }
            emptyView.setVisibility(emptyViewVisible ? View.VISIBLE : View.GONE);
        }
    }

    //============================================================================================================底部
    //上拉加载默认状态--默认为-1
    public int load_more_status = -1;

    //上拉加载更多
    public static final int LOADING_UP = 0;

    //正在加载中
    public static final int LOADING_MORE = 1;

    //加载结束
    public static final int LOADING_FINISH = 2;

    //加载成功
    public static final int LOADING_SUCCESS = 3;

    //加载失败
    public static final int LOADING_ERROR = 4;

    /**
     * @param status
     */
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }

    //============================================================================================================监听
    //以下接口对应各个item的点击事件
    public OnChildClickListener onChildClickListener;
    public OnItemClickListener onItemClickListener;
    public OnItemLongClickListener onItemLongClickListener;
    public OnSectionHeaderClickListener onSectionHeaderClickListener;
    public OnSectionFooterClickListener onSectionFooterClickListener;
    /**
     * 返回分组item的位置
     *
     * @param position
     * @return
     */
    public int getItemPosition(int position) {
        return positionWithinSection[position];
    }

    /**
     * 分组内的item点击回调
     */
    public interface OnItemClickListener {
        void onItemClick(int section, int position);
    }


    /**
     * item长按回调
     */
    public interface OnItemLongClickListener {
        void onItemLongClick(int section, int position);
    }

    /**
     * section的Header的点击回调
     */
    public interface OnSectionHeaderClickListener {
        void onSectionHeaderClick(int section);
    }

    /**
     * section的Footer的点击回调
     */
    public interface OnSectionFooterClickListener {
        void onSectionFooterClick(int section);
    }

    /**
     * 分组内子View点击事件回调，多了一个viewType，用以区分同一个item的不同的点击事件
     * 根据需求，需要时可实现此接口
     */
    public interface OnChildClickListener {

        /**
         * @param position item position
         * @param viewType 点击的view的类型，调用时根据不同的view传入不同的值加以区分，如viewType=0表示进入下一级页面，viewType=1表示查看大图等
         */
        void onChildClick(int position, int viewType);
    }

    public void setOnChildClickListener(OnChildClickListener onChildClickListener) {
        this.onChildClickListener = onChildClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public void setOnSectionHeaderClickListener(OnSectionHeaderClickListener onSectionHeaderClickListener) {
        this.onSectionHeaderClickListener = onSectionHeaderClickListener;
    }

    public void setOnSectionFooterClickListener(OnSectionFooterClickListener
                                                        onSectionFooterClickListener) {
        this.onSectionFooterClickListener = onSectionFooterClickListener;
    }

}
