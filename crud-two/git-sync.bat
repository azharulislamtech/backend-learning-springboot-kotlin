@echo off
echo === Git Commit, Pull and Push ===
echo.

git add .
echo Files staged for commit.
echo.

set /p message="Enter commit message: "
git commit -m "%message%"
echo.

echo Pulling latest changes...
git pull
echo.

echo Pushing changes...
git push
echo.

echo === Done ===
pause
