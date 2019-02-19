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
echo set ^"input^=%%input^:^~1^,^-1%%^"
echo %%input%%
echo for ^/f ^"delims^=^" %%%%i in ^( ^'reg query ^"HKEY_LOCAL^_MACHINE^\SOFTWARE^\Classes^\vmware^-view^\shell^\open^\command^"^'^) do set yourvar^=%%%%i
echo echo %%yourvar%%
echo set ^"acc^=%%yourvar^:^*^"^=%%^"
echo set ^"sc^=%%acc^:^~0^,^-22%%^"
echo echo %%sc%%
echo set ^"panfu^=^%%sc^:^~0^,2%%^"
echo echo %%panfu%%
echo %%panfu%%
echo cd %%sc%%
echo for /f ^"tokens=1 delims=^$^" %%%%a in ^(^"%%input%%^"^) do set serverUrl=%%%%a
echo echo %%serverUrl%%
echo set ^"serverUrl^=%%serverUrl^:^~9%%^"
echo for /f ^"tokens=2 delims=^$^" %%%%b in ^(^"%%input%%^"^) do set userName=%%%%b
echo echo %%userName%%
echo for /f ^"tokens=3 delims=^$^" %%%%c in ^(^"%%input%%^"^) do set password=%%%%c
echo echo %%password%%
echo for /f ^"tokens=4 delims=^$^" %%%%d in ^(^"%%input%%^"^) do set domainName=%%%%d
echo echo %%domainName%%
echo for /f ^"tokens=5 delims=^$^" %%%%e in ^(^"%%input%%^"^) do set desktopName=%%%%e
echo echo %%desktopName%%
echo echo vmware-view ^-^-serverUrl %%serverUrl%% ^-^-userName %%userName%% ^-^-password %%password%% ^-^-domainName %%domainName%% ^-^-desktopName %%desktopName%% ^-nonInteractive
echo vmware-view ^-^-serverUrl %%serverUrl%% ^-^-userName %%userName%% ^-^-password %%password%% ^-^-domainName %%domainName%% ^-^-desktopName %%desktopName%% ^-nonInteractive)>vm-url-start.bat
set "sc=%sc:\=\\%"
echo %sc%
:: 写注册表文件并执行
(echo Windows Registry Editor Version 5.00
echo ^[HKEY^_LOCAL^_MACHINE^\SOFTWARE^\Classes^\vmopen^]
echo ^@^=^"URL^:VMOPEN Protocol^"
echo ^"URL Protocol^"^=^"^"
echo ^"UseOriginalUrlEncoding^"^=dword^:00000001

echo ^[HKEY^_LOCAL^_MACHINE^\SOFTWARE^\Classes^\vmopen^\DefaultIcon^])>c.reg

echo ^@^=^"%sc%^\^\vm-url-start^.bat^,0^" >>c.reg

echo ^[HKEY^_LOCAL^_MACHINE^\SOFTWARE^\Classes^\vmopen^\shell^] >>c.reg

echo ^[HKEY^_LOCAL^_MACHINE^\SOFTWARE^\Classes^\vmopen^\shell^\open^] >>c.reg

echo ^[HKEY^_LOCAL^_MACHINE^\SOFTWARE^\Classes^\vmopen^\shell^\open^\command^] >>c.reg

echo ^@^=^"^\^"%sc%^\^\vm-url-start.bat^\^" ^\^"%%1^\^"^" >>c.reg

:: 结束
call c.reg

pause
