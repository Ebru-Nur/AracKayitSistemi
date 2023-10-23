import { createSlice } from "@reduxjs/toolkit";
export const user = createSlice({
  name: "user",
  initialState: {
    data: "null",
  },

  reducers: {
    setUser: (state, action) => {
      state.data = action.payload;
    },
  },
});

export const { setUser } = user.actions;
export default user.reducer;
