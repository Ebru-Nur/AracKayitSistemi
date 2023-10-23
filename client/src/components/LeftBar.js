import React, { useEffect } from "react";
import "../component.css";
import { Link, useNavigate } from "react-router-dom";
import Home from "../media/home.png";
import Pass from "../media/reset-password.png";
import Exit from "../media/exit.png";
import { useDispatch, useSelector } from "react-redux";
import {setUser } from "../stores/User";

const LeftBar = () => {
  const navigate = useNavigate();
  const user = useSelector((state) => state.user);
  const dispatch = useDispatch();
  const checkAuth = () => {
    if (user.data === null) {
      navigate("/login");
    }
  };

  useEffect(checkAuth);
  return (
    <div className="vh-100 bg-secondary sidebar p-3 d-flex flex-column gap-2">
      <h3 className="text-white pb-3"> EBSA CARS </h3>
      <button
        onClick={() => navigate("/")}
        className="ms-2 d-flex btn bg-white gap-2 "
      >
        {" "}
        <img
          className="text-white align-self-center"
          style={{ width: "30px" }}
          src={Home}
          alt="Home icon"
        />
        <p className="d-inline align-self-center my-auto"> Home</p>
      </button>
      <button
        onClick={() => navigate("/new-password")}
        className="ms-2 d-flex btn bg-white gap-2"
      >
        {" "}
        <img
          className="text-white align-self-center"
          style={{ width: "30px" }}
          src={Pass}
          alt="Home icon"
        />
        <p className="d-inline align-self-center my-auto">Change Password</p>
      </button>
      <button onClick={() => dispatch(setUser(null))} className="ms-2 d-flex btn gap-2 btn-danger">
        {" "}
        <img
          className="text-white align-self-center"
          style={{ width: "25px" }}
          src={Exit}
          alt="Exit icon"
        />
        <p className="d-inline align-self-center my-auto">Exit</p>
      </button>
    </div>
  );
};

export default LeftBar;
