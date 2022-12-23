#!/bin/bash

# Colors
RED='\033[0;31m'
LP='\033[1;35m'
NC='\033[0m' # No Color

# These are the values you'll change during benchmarking
BENCH_DOMAIN_URL="18.185.249.125:3000"
BENCH_NAME="spring-reactive"

BENCH_SLEEP=1000
BENCH_SIZE=50
BENCH_TRANSFORM="true"
BENCH_RATE=2000
BENCH_DURATION_SEC=300
BENCH_SKIP_DATA="false"

BENCH_RESULTS_NAME="transform_${BENCH_TRANSFORM}_sleep_${BENCH_SLEEP}_size_${BENCH_SIZE}_rate_${BENCH_RATE}_skipData_${BENCH_SKIP_DATA}"
BENCH_OUTPUT_DIR="output"
BENCH_OUTPUT="${BENCH_OUTPUT_DIR}/${BENCH_RESULTS_NAME}"

# Check if valid url
curl -s -I "http://${BENCH_DOMAIN_URL}/api/todos" || exit 1

echo ""
echo "Creating directory for output ${BENCH_OUTPUT}"
mkdir -p "${BENCH_OUTPUT}"
echo ""
echo -e "${LP}Benchmark started..."
date -u
echo ""
echo "BENCH_SLEEP=${BENCH_SLEEP}"
echo "BENCH_SIZE=${BENCH_SIZE}"
echo "BENCH_TRANSFORM=${BENCH_TRANSFORM}"
echo "BENCH_RATE=${BENCH_RATE}"
echo "BENCH_DURATION_SEC=${BENCH_DURATION_SEC}"
echo "BENCH_SKIP_DATA=${BENCH_SKIP_DATA}"
echo ""

FILE_NAME_RESULTS="${BENCH_NAME}.bin"
FILE_NAME_REPORT="${BENCH_NAME}.report.txt"
FILE_NAME_PLOT="${BENCH_NAME}.plot.html"
FILE_NAME_HDR="${BENCH_NAME}.hgrm"
FILE_NAME_HISTOGRAM="${BENCH_NAME}.histogram.txt"

echo GET "http://${BENCH_DOMAIN_URL}/api/todos?transform=${BENCH_TRANSFORM}&sleep=${BENCH_SLEEP}&size=${BENCH_SIZE}&skipData=${BENCH_SKIP_DATA}" | vegeta attack -name=${BENCH_NAME} -duration=${BENCH_DURATION_SEC}s -rate ${BENCH_RATE} | tee "${BENCH_OUTPUT}/${FILE_NAME_RESULTS}"  | vegeta report > "${BENCH_OUTPUT}/${FILE_NAME_REPORT}" || true
echo ""
echo "Results are stored in ${BENCH_OUTPUT}/ ${FILE_NAME_RESULTS} ${FILE_NAME_PLOT} ${FILE_NAME_REPORT} ${FILE_NAME_HDR} and ${FILE_NAME_HISTOGRAM}"

echo "Generating ${BENCH_OUTPUT}/${FILE_NAME_PLOT}"
cat "${BENCH_OUTPUT}/${FILE_NAME_RESULTS}" | vegeta plot > "${BENCH_OUTPUT}/${FILE_NAME_PLOT}" || true

echo "Generating histogram file ${BENCH_OUTPUT}/${FILE_NAME_HISTOGRAM}"
# Generate histogram from results
cat "${BENCH_OUTPUT}/${FILE_NAME_RESULTS}" | vegeta report -type="hist[0,300ms,1000ms,5000ms,20000ms]" > "${BENCH_OUTPUT}/${FILE_NAME_HISTOGRAM}"

echo "Generating hdr histogram plot ${BENCH_OUTPUT}/${FILE_NAME_HDR}"
cat "${BENCH_OUTPUT}/${FILE_NAME_RESULTS}" | vegeta report -type=hdrplot > "${BENCH_OUTPUT}/${FILE_NAME_HDR}"

echo "Done. You will use the .bin files at the end to combine plots"
date -u
echo -e "${NC}"
