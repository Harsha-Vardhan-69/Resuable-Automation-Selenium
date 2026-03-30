$raw = [Console]::In.ReadToEnd()

$editSignals = @(
    'apply_patch',
    'edit_notebook_file',
    'create_file',
    'vscode_renameSymbol'
)

$riskyTargets = @(
    'pom.xml',
    'src/test/resources/testng.xml',
    'src/main/java/com/framework/config/ConfigLoader.java',
    'src/main/java/com/framework/driver/WebDriverFactory.java',
    'src/main/java/com/framework/listeners/TestListener.java',
    'src/main/java/com/framework/reports/ExtentReport.java'
)

$isEdit = $false
foreach ($signal in $editSignals) {
    if ($raw -match [Regex]::Escape($signal)) {
        $isEdit = $true
        break
    }
}

$hits = @()
foreach ($target in $riskyTargets) {
    if ($raw -match [Regex]::Escape($target)) {
        $hits += $target
    }
}

if ($isEdit -and $hits.Count -gt 0) {
    $reason = "High-risk framework file edit detected: " + ($hits -join ', ') + ". Ask for confirmation or provide an impact note before proceeding."
    $output = @{
        hookSpecificOutput = @{
            hookEventName = 'PreToolUse'
            permissionDecision = 'ask'
            permissionDecisionReason = $reason
        }
    } | ConvertTo-Json -Compress
    [Console]::Out.Write($output)
    exit 0
}

$pass = @{
    hookSpecificOutput = @{
        hookEventName = 'PreToolUse'
        permissionDecision = 'allow'
        permissionDecisionReason = 'No risky framework edit detected.'
    }
} | ConvertTo-Json -Compress

[Console]::Out.Write($pass)