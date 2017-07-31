__author__ = 'pengtao'

import os

import logging
import pprint
import json
import urllib
import gw_file_system
from xml.etree import ElementTree as ET

logger = logging.getLogger('script.ysdk.py')
ANDROID_NS = 'http://schemas.android.com/apk/res/android'

def script(SDK, decompileDir, channelSdkInfo, new_game_channel_info, gameInfo):
    change_parames(decompileDir,new_game_channel_info)
    overwrite_assets_conf(decompileDir,new_game_channel_info)
    copyfile(decompileDir)

def copyfile(decompileDir):
    path_old = 'com.tencent.tmgp.kfbb.snake' + '.wxapi'
    old_path=path_old.replace('.','/')
    path= os.path.join(decompileDir, 'smali', old_path)
    xmlpath = os.path.join(decompileDir, 'AndroidManifest.xml')
    root_node = ET.parse(xmlpath)
    root = root_node.getroot()
    package_name = root.attrib.get('package')
    path_result = package_name + '.wxapi'
    newpath=path_result.replace('.','/')
    new_path_result = os.path.join(decompileDir, 'smali', newpath)

    if not os.path.exists(path):
        return
    gw_file_system.copy_files(path, new_path_result)
    replace_same_package(new_path_result, 'com.tencent.tmgp.kfbb.snake', package_name)


def replace_same_package(path, key, replaeKey):
    # com/loopj/android/http
    key = key.replace('.', '/')
    replaeKey = replaeKey.replace('.', '/')
    resmap = {}

    for root, dirs, files in os.walk(path):

        isWritefile = False

        if (len(files) <= 0):
            continue

        for file in files:

            sub_path = os.path.join(root, file)

            if (os.path.isfile(sub_path)):

                f = open(sub_path)

                lines = f.readlines()

                lines_write = []

                for line in lines:

                    if (line.find(key) != -1):
                        line = line.replace(key, replaeKey)
                        # print line

                        isWritefile = True

                    lines_write.append(line)

                f.close()

                if isWritefile:

                    f_handler = open(sub_path, 'w+')

                    f_handler.truncate()

                    f_handler.writelines(lines_write)

                    f_handler.close()

                    # shutil.move(sub_path,path)


def down_file_to_assets(decompileDir,new_game_channel_info):
     file_url = new_game_channel_info.get('data_obj').get('ysdk').get('ChannelParamFile')
     if file_url == '':
         return
     target_file = decompileDir+'/assets/ysdkconf.ini'
     if os.path.exists(target_file):
         os.remove(target_file)
     fp = open(target_file,'w')
     fp.close()
     urllib.urlretrieve(file_url, target_file)

def overwrite_assets_conf(decompileDir,new_game_channel_info):
    target_file = os.path.join(decompileDir, 'assets', 'ysdkconf.ini');
    fp = open(target_file,'w+')
    data = new_game_channel_info.get('data_obj', {})
    channel_data = data.get('ysdk', {})
    content=''

    content += 'QQ_APP_ID=' + str(channel_data.get('qqAppId', '')) + os.linesep + os.linesep

    content += 'WX_APP_ID=' + str(channel_data.get('weixinAppId', '')) + os.linesep + os.linesep

    content += 'OFFER_ID=' + str(channel_data.get('qqAppId', '')) + os.linesep  + os.linesep

    test_pre = ''
    release_pre = ''
    if str(channel_data.get('serverdebug', '1')) == '1':
        release_pre = ';'
    else:
        test_pre = ';'
    content += test_pre + 'YSDK_URL=https://ysdktest.qq.com'+ os.linesep + os.linesep

    content += release_pre +'YSDK_URL=https://ysdk.qq.com'+ os.linesep + os.linesep

    content += ';nitian'+ os.linesep
    fp.write(content)
    fp.close()

def change_parames(decompileDir,new_game_channel_info):
    qqAppId=new_game_channel_info.get('data_obj').get('ysdk').get('qqAppId')
    weixinAppId = new_game_channel_info.get('data_obj').get('ysdk').get('weixinAppId')
    xmlpath=os.path.join(decompileDir,'AndroidManifest.xml')
    root_node = ET.parse(xmlpath)
    root = root_node.getroot()
    name='{' + ANDROID_NS + '}name'
    scheme = '{' + ANDROID_NS + '}scheme'
    taskAffinity = '{' + ANDROID_NS + '}taskAffinity'
    package_name = root.attrib.get('package')

    if package_name is None:
        return
    activitys = root.findall("./application/activity")
    if activitys !=None:
        for activity in activitys:
            activityName=activity.attrib.get(name)
            if activityName=='com.tencent.tmgp.kfbb.snake.wxapi.WXEntryActivity':
                activity.set(name,package_name+'.wxapi.WXEntryActivity')
                activity.set(taskAffinity, package_name + '.diff')
                filterNode=activity.find('intent-filter')
                if filterNode != None:
                    dataNode=filterNode.find('data')
                    if dataNode !=None:
                        dataScheme=dataNode.attrib.get(scheme)
                        if dataScheme !=None:
                            dataNode.set(scheme,weixinAppId)
            if activityName=='com.tencent.tauth.AuthActivity':
                data=activity.find('intent-filter/data')
                if data !=None:
                    dataScheme=data.attrib.get(scheme)
                    if dataScheme!=None:
                        data.set(scheme,'tencent'+qqAppId)
    root_node.write(xmlpath,'utf-8')
if __name__ == '__main__':
    new_game_channel_info = {'data_obj': {
        u'ysdk': {'merchantId': 'cpid123', 'qqAppId': '551', 'weixinAppId': '8788', 'serverdebug': '0',
                   'channelId': 'channel888', 'code': 'channel888', 'orientation': '55555', 'gameName': 'wow','ChannelParamFile':'http://static.kuaifazs.com/upload/game/201511/11/cd/7b/HyPSaT.keystore'}}
    }
    #change_parames('C:/Users/Administrator/Desktop/YSDK', new_game_channel_info)
    #copyfile('C:/Users/Administrator/Desktop/YSDK')
    overwrite_assets_conf('C:/Users/Administrator/Desktop/YSDK', new_game_channel_info)
    #script('F:\\autopack2.0_kuaifa\\tool\\workspace\\huangshizhanzheng\\3933\\extract', 'F:\\autopack2.0_kuaifa\\tool\\workspace\\huangshizhanzheng\\3933\\extract', {}, new_game_channel_info, {})