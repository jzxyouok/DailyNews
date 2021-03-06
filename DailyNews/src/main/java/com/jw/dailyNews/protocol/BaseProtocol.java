package com.jw.dailyNews.protocol;

import android.content.Context;
import android.text.TextUtils;
import com.jw.dailyNews.utils.CacheUtils;

import Lib.NewsManager;
import Lib.callback.ObjectCallBack;

/**
 * 创建时间：2017/7/11
 * 更新时间：2017/11/11 0011 上午 12:18
 * 作者：Mr.jin
 * 描述：json数据三级缓存
 */
public abstract class BaseProtocol<T> {
    private Context mContext;
    public String mUrl;
    public String baseUrl;
    private String type;

    public BaseProtocol(String baseUrl,String url, Context context,String type){
        this.mContext=context;
        this.mUrl=url;
        this.baseUrl=baseUrl;
        this.type=type;
    }

    public T load(){
        String json = loadLocal();
        if(TextUtils.isEmpty(json)) {
            json = loadServer();
            if(!TextUtils.isEmpty(json)) {
                saveLocal(json);
                return parseJson(json);
            }
            else
                return null;
        }
        else
        return parseJson(json);
    }



    public String loadLocal(){
        return CacheUtils.getCacheString(mUrl, null);
    }

    public String loadServer(){
        return NewsManager.getInstance().doGet(mUrl,type);
    }
    public void loadServerAsy(ObjectCallBack callBack){
        NewsManager.getInstance().doGetAsy(mUrl,type,callBack);
    }

    private void saveLocal(String json){
        CacheUtils.setCache(mUrl,json);
    }

    public abstract T parseJson(String json);

}
