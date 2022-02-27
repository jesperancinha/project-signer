#!/bin/bash
JESPERANCINHAORGRESPONSE=$(curl http://jesperancinha.org)
META_TEST='<meta http-equiv="refresh" content="0;url=https://github.com/jesperancinha">'
SCRIPT_TEST='window.location.href = "https://github.com/jesperancinha";'
LINK_TEST='<a href="https://github.com/jesperancinha">redirect</a>'
if [[ "$JESPERANCINHAORGRESPONSE" =~ .*"$META_TEST".* ]];
then
  echo "META_TEST found!"
else
  echo "META_TEST data not found!"
  echo "$META_TEST"
  exit 1
fi
if [[ "$JESPERANCINHAORGRESPONSE" =~ .*"$SCRIPT_TEST".* ]];
then
  echo "SCRIPT_TEST found!"
else
  echo "SCRIPT_TEST data not found!"
  echo "$SCRIPT_TEST"
  exit 1
fi
if [[ "$JESPERANCINHAORGRESPONSE" =~ .*"$LINK_TEST".* ]];
then
  echo "LINK_TEST found!"
else
  echo "LINK_TEST data not found!"
  echo "$LINK_TEST"
  exit 1
fi
exit
