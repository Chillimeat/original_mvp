package indi.ayun.original_mvp.config;

import indi.ayun.original_mvp.base.UtilBase;
import indi.ayun.original_mvp.utils.verification.IsNothing;

public class CommonResult extends UtilBase {
    public static final int ZERO=0;
    public static final int SUCCESS=200;
    public static final int FAIL=-1;

    public String successMsg="";
    public String errorMsg="";
    public String msg="";
    public int result=Integer.MAX_VALUE;

    public CommonResult(int result) {
        onStructureMsg(result);
    }

    public CommonResult(String msg) {
        this.msg=msg;
    }

    public CommonResult(int result, String successMsg,String errorMsg) {
        this.result=result;
        this.successMsg = successMsg;
        this.errorMsg = errorMsg;
    }

    private void onStructureMsg(int result){
        switch (result){
            case ZERO:
                return;
            case SUCCESS:
                msg="执行成功！";
                break;
            case FAIL:
                msg="执行失败!";
                break;
        }
    }

    public String getMsg(){
        if (result==Integer.MAX_VALUE){
            return msg;
        }else {
            if (IsNothing.onAnything(successMsg)){
                return successMsg;
            }else {
                return errorMsg;
            }
        }

    }
}
