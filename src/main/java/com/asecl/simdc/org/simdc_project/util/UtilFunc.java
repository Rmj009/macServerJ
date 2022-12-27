package com.asecl.simdc.org.simdc_project.util;

import com.asecl.simdc.org.simdc_project.exception.QLException;
import com.asecl.simdc.org.simdc_project.graphql.entity.customEnum.EnumIncreaseType;
import com.asecl.simdc.org.simdc_project.graphql.entity.customEnum.EnumMacType;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class UtilFunc {

    private static Map<String, Integer> LOCK_KEYS =  UtilFunc.LOCK_KEYS = Collections.synchronizedMap(new HashMap<String, Integer>());

    public static long GetOUIMacMaxValue(String mac_1B, String mac_2B, String mac_3B){
        List<String> ouiMax = new ArrayList<>();
        ouiMax.add(mac_1B);
        ouiMax.add(mac_2B);
        ouiMax.add(mac_3B);
        ouiMax.add("ff");
        ouiMax.add("ff");
        ouiMax.add("ff");
        return UtilFunc.MacStringToMacLong(ouiMax);
    }

    public static long MacStringToMacLong(List<String> macs){
        long mac1 = Long.parseLong(macs.get(0), 16);
        long mac2 = Long.parseLong(macs.get(1), 16);
        long mac3 = Long.parseLong(macs.get(2), 16);
        long mac4 = Long.parseLong(macs.get(3), 16);
        long mac5 = Long.parseLong(macs.get(4), 16);
        long mac6 = Long.parseLong(macs.get(5), 16);
        return (mac1 << 40) | (mac2 << 32) | (mac3 << 24) | (mac4 << 16) | (mac5 << 8) | mac6;
    }

    public static String MacLongToMacString(long mac){
        String mac1 = String.format("%02x", (mac >> 40) & 0xff);
        String mac2 = String.format("%02x", (mac >> 32) & 0xff);
        String mac3 = String.format("%02x", (mac >> 24) & 0xff);
        String mac4 = String.format("%02x", (mac >> 16) & 0xff);
        String mac5 = String.format("%02x", (mac >> 8) & 0xff);
        String mac6 = String.format("%02x", (mac & 0xff));
        return mac1 + "-" + mac2 + "-" + mac3 + "-" + mac4 + "-" + mac5 + "-" + mac6;
    }

    public static List<String> CheckMacAddressFormat(String mac){
        if(mac.length() != 17){
            return null;
        }

        List<String> result = new ArrayList<String>();

        String split[] = mac.split("-");
        if(split.length != 6){
            return null;
        }

        for(int i = 0; i < 6 ; i++){
            long chkNum = Long.parseLong(split[i].trim(), 16);
            if(chkNum < 0 || chkNum > 0xff){
                return null;
            }else{
                result.add(split[i].trim());
            }
        }
        return result;
    }

    public static boolean CheckMacTypeInput(String macTypeInput){
        boolean isExist = false;
        for(EnumMacType item: EnumMacType.values()){
            if(macTypeInput.toLowerCase().equals(item.toString().toLowerCase())){
                isExist =  true;
                break;
            }
        }
        return isExist;
    }

    public static boolean CheckMacDispatchTypeInput(String macIncreaseTypeInput){
        boolean isExist = false;

        for(EnumIncreaseType item: EnumIncreaseType.values()){
            if(macIncreaseTypeInput.toLowerCase().equals(item.toString().toLowerCase())){
                isExist =  true;
                break;
            }
        }
        return isExist;
    }

    public static HttpServletRequest GetHttpServletRequest(){
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    public static boolean LockByKey(String key) throws InterruptedException {
        boolean lockStatus = false;
        boolean createKeyOK = false;
        int getKeyLoop = 80;
        int currentLoop = 0;
        while((++currentLoop) <= getKeyLoop){
            lockStatus = false;
            try
            {
                if(Constant.MAC_OP_LOCK.tryLock(50, TimeUnit.MICROSECONDS))
                {
                    lockStatus = true;
//                    if(UtilFunc.LOCK_KEYS == null){
//                        UtilFunc.LOCK_KEYS = Collections.synchronizedMap(new HashMap<String, Integer>());
//                    }

                    if(!UtilFunc.LOCK_KEYS.containsKey(key)){
                        UtilFunc.LOCK_KEYS.putIfAbsent(key, 1);
                        createKeyOK = true;
                        break;
                    }
                }
            }
            catch(Exception ex) {
            }finally {
                if(lockStatus){
                    Constant.MAC_OP_LOCK.unlock();
                }
            }
            Thread.sleep(100);
        }
        return createKeyOK;
    }

    public static void UnlockByKey(String key){
        boolean lockStatus = false;
        while(true){
            lockStatus = false;
            try
            {
                if(Constant.MAC_OP_LOCK.tryLock(5, TimeUnit.SECONDS))
                {
                    lockStatus = true;
                    if(UtilFunc.LOCK_KEYS == null){
                        break;
                    }
                    UtilFunc.LOCK_KEYS.remove(key);
                    break;
                }
            }
            catch(Exception ex) {
            }finally {
                if(lockStatus){
                    Constant.MAC_OP_LOCK.unlock();
                }
            }
        }
    }
}
