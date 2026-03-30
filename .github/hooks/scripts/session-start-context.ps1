$raw = [Console]::In.ReadToEnd()
$message = "Framework workflow: use .github/workflow/Framework.md as source of truth, use .github/workflow/tasks.md plus .github/workflow/changeLog.md for tracked work, and verify one task at a time."

$output = @{
    continue = $true
    systemMessage = $message
} | ConvertTo-Json -Compress

[Console]::Out.Write($output)