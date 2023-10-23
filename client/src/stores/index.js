import { configureStore } from "@reduxjs/toolkit";
import selectedCarReducer from "./SelectedCar.js";
import userReducer from "./User.js";

export default configureStore({
  reducer: {
    selectedCar : selectedCarReducer,
    user: userReducer,
  },
});
