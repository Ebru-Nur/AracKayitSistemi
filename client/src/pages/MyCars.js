import React from "react";
import Plus from "../media/plus.png";
import { useNavigate } from "react-router-dom";
import Edit from "../media/edit.png";
import Delete from "../media/delete.png";
import Profile from "../media/profile.png";
import { useDispatch } from "react-redux";
import { setSelectedCar } from "../stores/SelectedCar";

const MyCars = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const carData = [
    {
      id: 1,
      carName: "Car 1",
      brand: "Brand A",
      model: "Model X",
      year: 2022,
      numberPlate: "ABC 123",
    },
    {
      id: 2,
      carName: "Car 2",
      brand: "Brand B",
      model: "Model Y",
      year: 2021,
      numberPlate: "DEF 456",
    },
    {
      id: 3,
      carName: "Car 3",
      brand: "Brand C",
      model: "Model Z",
      year: 2023,
      numberPlate: "GHI 789",
    },
  ];

  return (
    <div className="w-100  bg-light p-4">
      <h3> My Cars </h3>
      <button
        onClick={() => navigate("/add-car")}
        className=" text-primary btn btn-white text-primary border border-primary rounded-0 d-flex gap-2"
      >
        {" "}
        <img
          className="text-white p-1"
          style={{ width: "25px", height: "25px" }}
          src={Plus}
          alt="Plus icon"
        />
        Add New Car
      </button>
      <div className="d-flex justify-content-center my-5 container">
        <table className="table  table-hover">
          <thead className="thead-dark">
            <tr className="table-active">
              <th scope="col">Car Name</th>
              <th scope="col">Brand</th>
              <th scope="col">Model</th>
              <th scope="col">Year</th>
              <th scope="col">Plate</th>
              <th scope="col">Options</th>
            </tr>
          </thead>
          <tbody>
            {carData.map((data) => (
              <tr key={data.id}>
                <td className="">
                  <img
                    className="rounded-circle p-1 border bg-light "
                    style={{ width: "30px" }}
                    src={Profile}
                    alt="profile"
                  />
                  <p className="px-2 d-inline">{data.carName}</p>
                </td>
                <td>{data.brand}</td>
                <td>{data.model}</td>
                <td>{data.year}</td>
                <td>{data.plate}</td>
                <td className="bg-light">
                  <div className="d-flex justify-content-center gap-3">
                    <button
                      onClick={() => {
                        dispatch(setSelectedCar(data));
                        navigate("/edit-car");
                      }}
                      type="button"
                      className="btn-light btn"
                    >
                      <img
                        className="p-1"
                        style={{ width: "25px" }}
                        src={Edit}
                        alt="edit"
                      />
                    </button>
                    <button type="button" className="btn-light btn">
                      <img
                        className="p-1"
                        style={{ width: "25px" }}
                        src={Delete}
                        alt="edit"
                      />
                    </button>
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default MyCars;
