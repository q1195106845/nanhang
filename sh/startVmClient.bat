@echo off
:: 获取VM注册表信息
for /f "delims=" %%i in ( 'reg query "HKEY_LOCAL_MACHINE\SOFTWARE\Classes\vmware-view\shell\open\command"') do set yourvar=%%i
echo %yourvar%
:: 截取目录
set "acc=%yourvar:*"=%"
set "exeText=%acc:~0,-6%"
echo %exeText%
set "sc=%acc:~0,-22%"
echo %sc%
:: 进入VM文件夹
set "panfu=%sc:~0,2%"
echo %panfu%
%panfu%
cd %sc%
:: 写bat文件
(echo @echo off
echo set input=%%1
echo echo %%input%%
echo for ^/f ^"delims^=^" %%%%i in ^( ^'reg query ^"HKEY_LOCAL^_MACHINE^\SOFTWARE^\Classes^\vmware^-view^\shell^\open^\command^"^'^) do set yourvar^=%%%%i
echo echo %%yourvar%%
echo set ^"acc^=%%yourvar^:^*^"^=%%^"
echo set ^"sc^=%%acc^:^~0^,^-22%%^"
echo echo %%sc%%
echo cd %%sc%%
echo for /f ^"tokens=2 delims=^$^" %%%%a in ^(^"%%input%%^"^) do set serverUrl=%%%%a
echo echo %%serverUrl%%
echo for /f ^"tokens=3 delims=^$^" %%%%b in ^(^"%%input%%^"^) do set userName=%%%%b
echo echo %%userName%%
echo for /f ^"tokens=4 delims=^$^" %%%%c in ^(^"%%input%%^"^) do set password=%%%%c
echo echo %%password%%
echo for /f ^"tokens=5 delims=^$^" %%%%d in ^(^"%%input%%^"^) do set domainName=%%%%d
echo echo %%domainName%%
echo for /f ^"tokens=6 delims=^$^" %%%%e in ^(^"%%input%%^"^) do set desktopName=%%%%e
echo echo %%desktopName%%
echo echo vmware-view ^-^-serverUrl %%serverUrl%% ^-^-userName %%userName%% ^-^-password %%password%% ^-^-domainName %%domainName%% ^-^-desktopName %%desktopName%% ^-nonInteractive
echo vmware-view ^-^-serverUrl %%serverUrl%% ^-^-userName %%userName%% ^-^-password %%password%% ^-^-domainName %%domainName%% ^-^-desktopName %%desktopName%% ^-nonInteractive)>vm-url-start.bat

:: 写注册表文件并执行
(echo Windows Registry Editor Version 5.00
echo ^[HKEY^_LOCAL^_MACHINE^\SOFTWARE^\Classes^\vmopen^]
echo ^@^=^"URL^:VMOPEN Protocol^"
echo ^"URL Protocol^"^=^"^"
echo ^"UseOriginalUrlEncoding^"^=dword^:00000001

echo ^[HKEY^_LOCAL^_MACHINE^\SOFTWARE^\Classes^\vmopen^\DefaultIcon^]
echo ^@^=^"C^:^\^\Program Files ^(x86^)^\^\VMware^\^\VMware Horizon View Client^\^\vm-url-start^.bat^,0^"

echo ^[HKEY^_LOCAL^_MACHINE^\SOFTWARE^\Classes^\vmopen^\shell^]

echo ^[HKEY^_LOCAL^_MACHINE^\SOFTWARE^\Classes^\vmopen^\shell^\open^]

echo ^[HKEY^_LOCAL^_MACHINE^\SOFTWARE^\Classes^\vmopen^\shell^\open^\command^]
:: echo
echo ^@^=^"^\^"C^:^\^\Program Files ^(x86^)^\^\VMware^\^\VMware Horizon View Client^\^\vm-url-start.bat^\^" ^\^"%%1^\^"^")>c.reg


:: 结束
call c.reg

pause
