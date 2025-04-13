#!/bin/bash

# Requires:
# - GitHub CLI: https://cli.github.com/
# - jq: https://stedolan.github.io/jq/
# - GH_TOKEN must be set as an environment variable (with `notifications` + `repo` scopes)

if [ -z "$GH_TOKEN" ]; then
  echo "❌ Please set the GH_TOKEN environment variable (your Personal Access Token)."
#  exit 1
fi

today=$(date -u +%Y-%m-%d)
echo "📅 Today's date (UTC): $today"
echo "🔄 Fetching ALL notifications (read and unread)..."

gh api notifications?all=true --paginate \
  | jq -r '.[] | select(.subject.url | contains("/pulls/")) | [.id, .url, .subject.url] | @tsv' \
  | while IFS=$'\t' read -r notification_id thread_url pr_url; do

    echo "🔍 Checking PR: $pr_url"

    pr_data=$(gh api "$pr_url" 2>/dev/null)
    if [ -z "$pr_data" ]; then
      echo "⚠️  Failed to fetch PR data. Skipping."
      continue
    fi

    merged=$(echo "$pr_data" | jq '.merged')
    merged_at=$(echo "$pr_data" | jq -r '.merged_at')
    merged_date=${merged_at:0:10}  # extract YYYY-MM-DD
    state=$(echo "$pr_data" | jq -r '.state')

    if [ "$merged" = "true" ] && [ "$merged_date" = "$today" ]; then
      echo "✅ PR merged today ($merged_date). Marking notification as done."
      echo "Patching: $thread_url"
      gh api -X PATCH "$thread_url" -f status=done
    else
      echo "⏭️  Skipping. Merged: $merged | State: $state | Merged date: $merged_date"
    fi
done
