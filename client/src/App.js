import { BrowserRouter, Route, Routes } from "react-router-dom"
import PageContainer from "./pages/PageContainer";
import MyCars from "./pages/MyCars";
import NewCar from "./pages/NewCar";
import EditCar from "./pages/EditCar";
import NewPass from "./pages/NewPass";
import Login from "./pages/Login";
import Signup from "./pages/Signup";
function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route element={<PageContainer/>} >
          <Route element={<MyCars/>} path="/" exact/>
          <Route element={<NewCar/>} path="/add-car"/>
          <Route element={<EditCar/>} path="/edit-car"/>
          <Route element={<NewPass/>} path="/new-password" />
        </Route>
        <Route element={<Login/>} path="/login"/>
        <Route element={<Signup/>} path="/signup"/>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
