#include <iostream>
#include <vector>
#include <string>
#include <iomanip>
using namespace std;

class ParkingSpace {
public:
    int number;
    string plate;

    ParkingSpace(int n) : number(n), plate("") {}
    bool isAvailable() const { return plate.empty(); }
};

class ParkingLot {
private:
    vector<ParkingSpace> spaces;
    const double hourlyRate = 5.00;

public:
    ParkingLot(int capacity) {
        for (int i = 1; i <= capacity; i++) spaces.emplace_back(i);
    }

    void parkVehicle(const string& plate) {
        for (const auto& s : spaces) {
            if (s.plate == plate) {
                cout << "Vehicle is already parked.\n";
                return;
            }
        }
        for (auto& s : spaces) {
            if (s.isAvailable()) {
                s.plate = plate;
                cout << "Vehicle " << plate << " parked in space " << s.number << ".\n";
                return;
            }
        }
        cout << "Parking lot is full.\n";
    }

    void removeVehicle(const string& plate, int hours) {
        if (hours < 1) {
            cout << "Hours must be at least 1.\n";
            return;
        }
        for (auto& s : spaces) {
            if (s.plate == plate) {
                s.plate = "";
                cout << "Vehicle " << plate << " exited. Fee: $"
                     << fixed << setprecision(2) << hours * hourlyRate << "\n";
                return;
            }
        }
        cout << "Vehicle not found.\n";
    }

    void showStatus() const {
        int occupied = 0;
        for (const auto& s : spaces) {
            if (!s.isAvailable()) occupied++;
            cout << "Space " << s.number << ": "
                 << (s.isAvailable() ? "Available" : s.plate) << "\n";
        }
        cout << "Occupied: " << occupied
             << " | Available: " << spaces.size() - occupied << "\n";
    }
};

int main() {
    ParkingLot lot(10);
    int choice = 0;
    string plate;
    int hours;

    do {
        cout << "\nPARKING MANAGEMENT SYSTEM\n"
             << "1. Park vehicle\n2. Remove vehicle\n"
             << "3. Show parking status\n4. Exit\nChoose: ";
        if (!(cin >> choice)) break;
        switch (choice) {
            case 1:
                cout << "Enter licence plate: ";
                cin >> plate;
                lot.parkVehicle(plate);
                break;
            case 2:
                cout << "Enter licence plate: ";
                cin >> plate;
                cout << "Enter hours parked: ";
                if (!(cin >> hours)) return 1;
                lot.removeVehicle(plate, hours);
                break;
            case 3:
                lot.showStatus();
                break;
            case 4:
                cout << "Goodbye!\n";
                break;
            default:
                cout << "Invalid option.\n";
        }
    } while (choice != 4);
}
