import { createSlice } from "@reduxjs/toolkit";
export const selectedCar = createSlice({
  name: "selectedCar",
  initialState: { current: null },

  reducers: {
    setSelectedCar: (state, action) => {
      state.current = action.payload;
    },
  },
});

export const { setSelectedCar } = selectedCar.actions;
export default selectedCar.reducer;
