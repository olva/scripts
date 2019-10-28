$srv = Get-Service -Name XblAuthManager
$srv1= Get-Service -Name XblGameSave
$srv2= Get-Service -Name XboxNetApiSvc
$srvName= $srv.DisplayName
$srv1Name= $srv1.DisplayName
$srv2Name= $srv2.DisplayName


if($srv2.Status -eq "Running")
{
    $srv2.Stop()
    Start-Sleep -Seconds 5
    $srv2.Refresh()
}

 Write-Output  "$srv2Name is stopped "

if($srv1.Status -eq "Running")
{
    $srv1.Stop()
    Start-Sleep -Seconds 5
    $srv1.Refresh()
}

 Write-Output  "$srv1Name is stopped "

if($srv.Status -eq "Running")
{
    $srv.Stop()
    Start-Sleep -Seconds 5
    $srv.Refresh()
}

 Write-Output  "$srvName is stopped "



if($srv.Status -eq "Stopped")
{
    $srv.Start()
    Start-Sleep -Seconds 5
    $srv.Refresh()
}

 Write-Output "$srvName is Running"

if($srv1.Status -eq "Stopped")
{
    $srv1.Start()
    Start-Sleep -Seconds 5
    $srv1.Refresh()
}

 Write-Output "$srv1Name is Running"

if($srv2.Status -eq "Stopped")
{
    $srv2.Start()
    Start-Sleep -Seconds 5
    $srv2.Refresh()
}

    Write-Output "$srv2Name is Running"
