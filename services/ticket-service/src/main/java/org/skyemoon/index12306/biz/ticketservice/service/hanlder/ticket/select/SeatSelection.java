package org.skyemoon.index12306.biz.ticketservice.service.hanlder.ticket.select;

import cn.hutool.core.collection.CollUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 座位选择器
 */
public class SeatSelection {

    public static int[][] adjacent(int numSeats, int[][] seatLayout) {
        int numRows = seatLayout.length;
        int numCols = seatLayout[0].length;
        List<int[]> selectedSeats = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (seatLayout[i][j] == 0) {
                    int consecutiveSeats = 0;
                    for (int k = j; k < numCols; k++) {
                        if (seatLayout[i][k] == 0) {
                            consecutiveSeats ++;
                            if (consecutiveSeats == numSeats) {
                                for (int l = k - numSeats + 1; l <= k; l++) {
                                    selectedSeats.add(new int[]{i, l});
                                }
                                break;
                            }
                        } else {
                            consecutiveSeats = 0;
                        }
                    }
                    if (! selectedSeats.isEmpty()) break;
                }
            }
            if (!selectedSeats.isEmpty()) break;
        }
        if (CollUtil.isEmpty(selectedSeats)) return null;

        int[][] actualSeat = new int[numSeats][2];
        int i = 0;
        for (int[] seat : selectedSeats) {
            int row = seat[0] + 1;
            int col = seat[1] + 1;
            actualSeat[i][0] = row;
            actualSeat[i][1] = col;
            i++;
        }
        return actualSeat;
    }
}
