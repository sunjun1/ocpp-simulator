# encoding=utf-8

import os
import json

# print("hello")


# 分析操作
def AnalyseOperation(operationName):
    f = open(operationName + ".json", encoding='utf-8')
    res = f.read()
    print(json.loads(res))


# 分析应答
def AnalyseOperationResponse(operationName):
    f = open(operationName + "Response.json", encoding='utf-8')
    res = f.read()
    print(json.loads(res))


# 分析一个操作和一个应答
def AnalyseOperationAndResponse(operationName):
    AnalyseOperation(operationName)
    AnalyseOperationResponse(operationName)


# 遍历所有json, 找出每个操作和应答匹配对进行分析
def ListAllFiles(rootdir):
    count = 0
    for dirPaths, dirNames, fileNames in os.walk(rootdir):
        for name in fileNames:
            if name.endswith("Response.json"):
                # print(name)
                count += 1 
                operationName = name[:-13]
                print(operationName)
                AnalyseOperationAndResponse(operationName)

    print("total: " + str(count))


def main():
    ListAllFiles(".\\")


if __name__ == '__main__':
    main()