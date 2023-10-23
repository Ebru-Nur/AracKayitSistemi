import React, { useEffect, useState } from "react";
import Save from "../media/floppy-disk.png";
import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";

const EditCar = () => {
    const selectedCar = useSelector((state) => state.selectedCar);
  const [data, setData] = useState(selectedCar.current);
  const navigate = useNavigate();

  return (
    <div className="bg-light w-100 p-4">
      <h3 className="font-weight-bold"> Edit Car </h3>
      <div className=" d-flex flex-column gap-3 align-content-center justify-content-center h-75 ">
        <div className="w-50 align-self-center">
          <label htmlFor="text"> Car Name </label>
          <input
            type="text"
            className="form-control"
            value={data.carName}
            onChange={(e) => setData({ ...data, carName: e.target.value })}
            required
            placeholder="Car Name"
          />
        </div>
        <div className="w-50 align-self-center">
          <label htmlFor="text"> Number Plate </label>
          <input
            type="text"
            className="form-control"
            value={data.numberPlate}
            onChange={(e) => setData({ ...data, numberPlate: e.target.value })}
            required
            placeholder="Number Plate"
          />
        </div>
        <div className="w-50 align-self-center">
          <label htmlFor="text"> Brand </label>
          <input
            type="text"
            className="form-control"
            value={data.brand}
            onChange={(e) => setData({ ...data, brand: e.target.value })}
            required
            placeholder="Brand "
          />
        </div>
        <div className="w-50 align-self-center">
          <label htmlFor="text"> Model </label>
          <input
            type="text"
            className="form-control"
            value={data.model}
            onChange={(e) => setData({ ...data, model: e.target.value })}
            required
            placeholder="Model "
          />
        </div>
        <div className="w-50 align-self-center">
          <label htmlFor="text"> Year </label>
          <input
            type="text"
            className="form-control"
            value={data.year}
            onChange={(e) => setData({ ...data, year: e.target.value })}
            required
            placeholder="Year "
          />
        </div>
        <div className="w-50 align-self-center d-flex justify-content-end gap-3">
          <button
            onClick={() => {
              navigate("/");
            }}
            className="btn btn-white text-primary  "
          >
            {" "}
            Cancel{" "}
          </button>
          <button className="btn btn-primary ">
            {" "}
            Save{" "}
            <img
              className="p-1"
              style={{ width: "25px", height: "25px" }}
              src={Save}
              alt="Save"
            />
          </button>
        </div>
      </div>
    </div>
  );
};

export default EditCar;
